package com.trense.hash;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/**
 * The base class for implementations of hashing algorithms for streaming data.
 * by calling the {@link #hash(byte[])}-function the hash will be produced an
 * the internal state will be reset.
 *
 * @author Tim Trense
 */
public abstract class StreamHashBuilder<T extends Hash> extends HashBuilder<T> {

    /**
     * allows hashes to be computed continuously
     *
     * @param data the data to insert to the hash
     * @param off  offset
     * @param len  length
     */
    public abstract void update( final @NotNull byte[] data,
            final @PositiveOrZero int off, final @Positive int len );

    /**
     * initializes the hash builder for a new hashing process
     */
    public abstract void reset();

    /**
     * allows hashes to be computed continuously
     *
     * @param data the data to insert to the hash
     */
    public final void update( final @NotNull byte[] data ) {
        update( data, 0, data.length );
    }

    /**
     * completes the hashing and resets the hash builder
     *
     * @return the result of {@link #hash(byte[], int, int)} if no further data would be supplied
     */
    public abstract T hash();

    @Override
    public T hash( final @NotNull byte[] data,
            final @PositiveOrZero int off, final @Positive int len ) {
        reset();
        update( data, off, len );
        return hash();
    }
}
