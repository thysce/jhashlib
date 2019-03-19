package com.trense.hash;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.security.SecureRandom;

/**
 * A builder for secure hashes.
 * An instance of this class should only be used once, because the internally stored salt is meant to
 * vary for each data to be hashed
 *
 * @author Tim Trense
 * @see #buildSalt(int)
 */
public abstract class SecureHashBuilder<T extends SecureHash> extends HashBuilder<T> {

    /**
     * The used secure random numbers generator.
     * May be changed at runtime to a custom, system wide SecureRandom
     */
    public static @NotNull SecureRandom secureRandom;

    static {
        secureRandom = new SecureRandom();
        // warm up the random number generator
        Thread initializer = new Thread() {
            @Override
            public void run() {
                for ( int i = 0; i < secureRandom.nextInt( 1000 ); i++ ) {
                    secureRandom.nextLong();
                }
            }
        };
        try {
            initializer.setName( SecureHashBuilder.class.getName() + ".secureRandom.initializer" );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        initializer.start();
    }

    private @NotNull byte[] salt;
    private @Positive int iterations;

    public SecureHashBuilder( final @NotNull byte[] salt, final @Positive int iterations ) {
        this.salt = salt;
        this.iterations = iterations;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt( final @NotNull byte[] salt ) {
        this.salt = salt;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations( final @Positive int iterations ) {
        this.iterations = iterations;
    }

    /**
     * computes a securely random salt of the given length
     *
     * @param length the number of bytes in the result salt
     * @return a salt
     */
    public static byte[] buildSalt( final @Positive int length ) {
        final byte[] salt = new byte[length];
        secureRandom.nextBytes( salt );
        return salt;
    }

    @Override
    public abstract T hash( final @NotNull byte[] data, final int off, final int len );

}
