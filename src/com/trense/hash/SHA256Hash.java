package com.trense.hash;

import javax.validation.constraints.NotNull;

/**
 * A hash based on the SHA-256 algorithm
 * 
 * @author Tim Trense
 *
 */
public class SHA256Hash extends Hash {

	public SHA256Hash(final @NotNull byte[] hash, final @NotNull SHA256HashBuilder builder) {
		super(hash, builder);
	}

	@Override
	public @NotNull SHA256HashBuilder getBuilder() {
		return (@NotNull SHA256HashBuilder) super.getBuilder();
	}

}
