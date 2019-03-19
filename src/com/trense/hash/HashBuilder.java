package com.trense.hash;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.nio.charset.StandardCharsets;

/**
 * The base class for implementations of hashing algorithms
 *
 * @author Tim Trense
 */
public abstract class HashBuilder<T extends Hash> {

    /**
     * @return the hashing algorithms name
     */
    public abstract @NotBlank String getAlgorithm();

    /**
     * computes a hash of the given data
     *
     * @param data the data to be hashed
     * @param off  offset
     * @param len  length
     * @return the computed hash or null if the algorithm failed
     */
    public abstract T hash( final @NotNull byte[] data,
            final @PositiveOrZero int off, final @Positive int len );

    /**
     * computes a hash of the given data
     *
     * @param data the data to be hashed
     * @return the computed hash or null if the algorithm failed
     */
    public final T hash( final @NotNull byte[] data ) {
        return hash( data, 0, data.length );
    }

    /**
     * computes a hash of the given data encoded as UTF-8.
     * Subclasses may override this behavior.
     *
     * @param data the data to be hashed
     * @return the computed hash or null if the algorithm failed
     */
    public T hash( final @NotNull String data ) {
        byte[] utf8Data = data.getBytes( StandardCharsets.UTF_8 );
        return hash( utf8Data );
    }
}
