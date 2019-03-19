package com.trense.hash;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Arrays;

/**
 * A hash that was computed using salt and iterative algorithms
 *
 * @author Tim Trense
 */
public class SecureHash extends Hash {

    private final @NotNull byte[] salt;
    private final @Positive int iterations;

    /**
     * instantiate a hash that is generated using the algorithm multiple times
     *
     * @param hash       the generated hash
     * @param salt       the salt used
     * @param builder    the generator of this hash
     * @param iterations how many times the algorithm was applied
     */
    public SecureHash( final @NotNull byte[] hash, final @NotBlank SecureHashBuilder<?> builder, final @NotNull byte[] salt,
            final @Positive int iterations ) {
        super( hash, builder );
        this.salt = salt;
        this.iterations = iterations;
    }

    /**
     * @return the used salt
     */
    public @NotNull byte[] getSalt() {
        return salt;
    }

    /**
     * @return how many times the algorithm was applied
     */
    public @Positive int getIterations() {
        return iterations;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( !( obj instanceof SecureHash ) ) {
            return false;
        }
        SecureHash other = (SecureHash)obj;
        if ( this.iterations != other.iterations ) {
            return false;
        }
        if ( !Arrays.equals( this.salt, other.salt ) ) {
            return false;
        }
        return super.equals( other );
    }

    @Override
    public int hashCode() {
        int h = super.hashCode() ^ iterations;
        for ( int i = 0; i < salt.length; i++ ) {
            h ^= salt[i] << ( ( i % 4 ) * 8 );
        }
        return h;
    }

    @Override
    public @NotNull SecureHashBuilder<?> getBuilder() {
        return (@NotNull SecureHashBuilder<?>)super.getBuilder();
    }
}
