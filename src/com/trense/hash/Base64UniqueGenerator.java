package com.trense.hash;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Base64;

/**
 * Generator for unique random URL-safe Base64 strings
 *
 * @author Tim Trense
 * @see #setLength(int) to adjust the length of the next generated strings
 */
public class Base64UniqueGenerator extends RandomUniqueGenerator<String> {

    private int length;

    public Base64UniqueGenerator(@Positive int length, @NotNull CheckUniqueFunction<String> uniqueFunction) {
        super(uniqueFunction);
        setLength(length);
    }

    public void setLength(@Positive int length) {
        this.length = length;
    }

    public @Positive int getLength() {
        return length;
    }

    @Override
    protected @NotNull String createRandom() {
        byte[] h = new byte[length];
        getRandom().nextBytes(h);
        return Base64.getUrlEncoder().encodeToString(h).substring(0, length);
    }
}
