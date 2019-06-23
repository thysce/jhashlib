package com.trense.hash;

import javax.validation.constraints.NotNull;
import java.security.SecureRandom;

/**
 * The implementation of a simple algorithm to create random, but unique items, eg. IDs, Handles etc
 *
 * @author Tim Trense
 */
public abstract class RandomUniqueGenerator<T> {

    private final SecureRandom random;
    private final CheckUniqueFunction<T> uniqueFunction;

    /**
     * Instantiates a new generator
     *
     * @param uniqueFunction the function to validate the uniqueness of generated items
     * @param random         the randomizer algorithm (should be hidden by subclass constructors)
     */
    public RandomUniqueGenerator(final @NotNull CheckUniqueFunction<T> uniqueFunction, final @NotNull SecureRandom random) {
        this.uniqueFunction = uniqueFunction;
        this.random = random;
    }

    /**
     * Convenience function to fast-forward the complete constructor
     *
     * @param uniqueFunction the function to validate the uniqueness of generated items
     */
    public RandomUniqueGenerator(final @NotNull CheckUniqueFunction<T> uniqueFunction) {
        this(uniqueFunction, new SecureRandom());
    }

    /**
     * @return the internal randomization instance
     */
    public @NotNull SecureRandom getRandom() {
        return random;
    }

    /**
     * @return the uniqueness validation algorithm for this generator
     */
    public @NotNull CheckUniqueFunction<T> getUniqueFunction() {
        return uniqueFunction;
    }

    /**
     * Tries to compute a random unique value.
     * Callers must ensure that computation of such a value can be done by trying out multiple
     * random values until one is unique. Especially callers must ensure that there is such a value in
     * the context of the given uniqueness function. Furthermore callers should ensure that such a value
     * can be found quickly (so there should be enough free space in the value space of the generic type parameter)
     *
     * @return a random and unique value
     */
    public T compute() {
        T item;
        do {
            item = createRandom();
        } while (!uniqueFunction.checkUnique(item));
        return item;
    }

    /**
     * Subclasses must implement an algorithm to create a randomized instance of the generic type parameter
     *
     * @return a random item
     */
    protected abstract @NotNull T createRandom();
}
