package com.trense.hash;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * The generator for hashes of the password-based-key-derivation-function-2
 * encoded key that was build upon the HMAC-on-SHA-256-algorithm<br>
 * the algorithms name is (without quotes) "PBKDF2WithHmacSHA256"
 *
 * @author Tim Trense
 */
public class PBKDF2WithHmacSHA256HashBuilder extends SecureHashBuilder<PBKDF2WithHmacSHA256Hash> {

    public PBKDF2WithHmacSHA256HashBuilder( final @NotNull byte[] salt, final @Positive int iterations ) {
        super( salt, iterations );
    }

    @Override
    public PBKDF2WithHmacSHA256Hash hash( final @NotNull byte[] data, final int off, final int len ) {
        final CharBuffer cb = StandardCharsets.UTF_8.decode( ByteBuffer.wrap( data, off, len ) );
        if ( cb != null ) {
            return hash( cb.array() );
        }
        else {
            return null;
        }
    }

    /**
     * computes a hash of the given data like {@link #hash(char[])} with the character array given by the parameter
     *
     * @param data the data to be hashed
     * @return the computed hash or null if the algorithm failed
     */
    @Override
    public final PBKDF2WithHmacSHA256Hash hash( final @NotNull String data ) {
        return hash( data.toCharArray() );
    }

    /**
     * computes the actual hash based on the instance's salt and iterations
     *
     * @param data the data to hash
     * @return the actual hash
     */
    public PBKDF2WithHmacSHA256Hash hash( final @NotNull char[] data ) {
        try {
            final byte[] salt = getSalt();
            final int iterations = getIterations();
            final PBEKeySpec spec = new PBEKeySpec( data, salt, iterations, 256 /* key length */ );
            final SecretKeyFactory fact = SecretKeyFactory.getInstance( getAlgorithm() );
            if ( fact == null ) {
                return null;
            }
            final SecretKey key = fact.generateSecret( spec );
            if ( key == null ) {
                return null;
            }
            return new PBKDF2WithHmacSHA256Hash( key.getEncoded(), this, salt, iterations );
        }
        catch ( final InvalidKeySpecException | NoSuchAlgorithmException e ) {
            return null;
        }
    }

    @Override
    public @NotBlank String getAlgorithm() {
        return "PBKDF2WithHmacSHA256";
    }


    /**
     * computes a hash of the given data with given salt and iterations
     *
     * @param data       the data to hash
     * @param salt       the salt to vary the data-hash with
     * @param iterations the iteration count for the algorithm
     * @return the actual hash, or null if the algorithm failed
     */
    public static PBKDF2WithHmacSHA256Hash buildHash( final byte[] data, final byte[] salt, final int iterations ) {
        final PBKDF2WithHmacSHA256HashBuilder hb = new PBKDF2WithHmacSHA256HashBuilder( salt, iterations );
        return hb.hash( data );
    }

    /**
     * computes a hash of the given data with given salt and iterations
     *
     * @param data       the data to hash
     * @param salt       the salt to vary the data-hash with
     * @param iterations the iteration count for the algorithm
     * @return the actual hash, or null if the algorithm failed
     */
    public static PBKDF2WithHmacSHA256Hash buildHash( final String data, final byte[] salt, final int iterations ) {
        final PBKDF2WithHmacSHA256HashBuilder hb = new PBKDF2WithHmacSHA256HashBuilder( salt, iterations );
        return hb.hash( data );
    }

    /**
     * computes a hash of the given data with reasonable salt and iterations
     *
     * @param data the data to hash
     * @return the actual hash, or null if the algorithm failed
     */
    public static PBKDF2WithHmacSHA256Hash buildHash( final byte[] data ) {
        return buildHash( data, buildSalt( 64 ), 5_000 );
    }

    /**
     * computes a hash of the given data with reasonable salt and iterations
     *
     * @param data the data to hash
     * @return the actual hash, or null if the algorithm failed
     */
    public static PBKDF2WithHmacSHA256Hash buildHash( final String data ) {
        return buildHash( data, buildSalt( 64 ), 5_000 );
    }
}
