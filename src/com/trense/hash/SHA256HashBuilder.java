package com.trense.hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * The generator for {@link SHA256Hash}
 *
 * @author Tim Trense
 */
public class SHA256HashBuilder extends StreamHashBuilder<SHA256Hash> {

    private MessageDigest digest;

    public SHA256HashBuilder() {
        try {
            digest = MessageDigest.getInstance( getAlgorithm() );
        }
        catch ( final NoSuchAlgorithmException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public @NotBlank String getAlgorithm() {
        return "SHA-256";
    }

    /**
     * encodes the given data as UTF-8 and hashes the result
     *
     * @param data the data to be hashed
     * @return the actual hash or null if the algorithm failed
     */
    public SHA256Hash hash( final @NotBlank String data ) {
        return hash( data.getBytes( StandardCharsets.UTF_8 ) );
    }

    @Override
    public void update( @NotNull byte[] data, final int off,
            final int len ) {
        digest.update( data, off, len );
    }

    @Override
    public void reset() {
        digest.reset();
    }

    @Override
    public SHA256Hash hash() {
        return new SHA256Hash( digest.digest(), this );
    }

    /**
     * hashes the data
     *
     * @param data the data to be hashed
     * @return the actual hash or null if the algorithm failed
     */
    public static SHA256Hash buildHash( final byte[] data ) {
        final SHA256HashBuilder hb = new SHA256HashBuilder();
        return hb.hash( data );
    }

    /**
     * encodes the given data as UTF-8 and hashes the result
     *
     * @param data the data to be hashed
     * @return the actual hash or null if the algorithm failed
     */
    public static SHA256Hash buildHash( final String data ) {
        final SHA256HashBuilder hb = new SHA256HashBuilder();
        return hb.hash( data );
    }

}
