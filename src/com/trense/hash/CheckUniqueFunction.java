package com.trense.hash;

/**
 * The user-callback function to check anything for uniqueness in a specified context
 *
 * @author Tim Trense
 */
public interface CheckUniqueFunction<T> {

    /**
     * Checks whether the item is unique in its context or would be unique if it was added to the context
     * @param item the element to check for uniqueness
     * @return true if it is or would be unique with the context this function is validating in
     */
    boolean checkUnique(T item);
}
