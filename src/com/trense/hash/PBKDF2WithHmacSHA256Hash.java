package com.trense.hash;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * A hash that was computed using the HMAC-SHA-256-algorithm along a PBKDF-2
 * iterator
 * 
 * @author Tim Trense
 *
 */
public class PBKDF2WithHmacSHA256Hash extends SecureHash {

	public PBKDF2WithHmacSHA256Hash(final @NotNull byte[] hash, final @NotNull PBKDF2WithHmacSHA256HashBuilder builder,
			final @NotNull byte[] salt, final @Positive int iterations) {
		super(hash, builder, salt, iterations);
	}

	@Override
	public @NotNull PBKDF2WithHmacSHA256HashBuilder getBuilder() {
		return (@NotNull PBKDF2WithHmacSHA256HashBuilder) super.getBuilder();
	}

}
