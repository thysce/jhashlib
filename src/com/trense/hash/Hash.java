package com.trense.hash;

import java.math.BigInteger;
import java.util.Arrays;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * A hash value computed based on some customizable data and algorithm.
 *
 * @author Tim Trense
 */
public class Hash {

    private final @NotNull HashBuilder<?> builder;
    private final @NotNull byte[] hash;

    /**
     * instantiate from a given hash that was computed from the builder
     *
     * @param hash    the hash value
     * @param builder the generator of this hash
     */
    public Hash( final @NotNull byte[] hash,
            final @NotNull HashBuilder<?> builder ) {
        this.hash = hash;
        this.builder = builder;
    }

    /**
     * @return the raw hash value
     */
    public @NotNull byte[] getHash() {
        return getRaw();
    }

    /**
     * @return the raw hash value
     */
    public @NotNull byte[] getRaw() {
        return hash;
    }

    /**
     * forwards {@link #getBuilder()}.{@link HashBuilder#getAlgorithm()}
     *
     * @return the algorithm used to compute the hash
     */
    public @NotBlank String getAlgorithm() {
        return builder.getAlgorithm();
    }

    /**
     * @return the generator of this hash
     */
    public @NotNull HashBuilder<?> getBuilder() {
        return builder;
    }

    @Override
    public int hashCode() {
        int h = 0;
        for ( int i = 0; i < this.hash.length; i++ ) {
            h ^= this.hash[i] << ( ( i % 4 ) * 8 );
        }
        return h;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj == null ) {
            return false;
        }
        if ( obj == this ) {
            return true;
        }
        if ( obj instanceof Hash ) {
            return Arrays.equals( this.hash, ( (Hash)obj ).hash );
        }
        if ( obj instanceof byte[] ) {
            return Arrays.equals( this.hash, (byte[])obj );
        }
        return false;
    }

    @Override
    public String toString() {
        final BigInteger i = new BigInteger( hash );
        return i.toString();
    }
}