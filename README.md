# jhashlib
A java convenience wrapper for low level hashing functionality

Dependencies:
 (maven) javax.validation:validation-api:2.0.1.Final

## Using jhashlib
Currently there are two hashing functions available: SHA-256 and PBKDF2-with-HMAC-SHA-256.
SHA-256 might be used for any large data that does not need to be stored securely.
PBKDF2-with-HMAC-SHA-256 should be used for storing secured hashes eg. of passwords in a database. This is a quite reasonable security directive (but as always: nothing ever is 100% secure).

To generate a SHA-256 hash use:

    SHA256Hash hash = SHA256HashBuilder.buildHash("Hello World");
    byte[] raw = hash.getHash();
    assert hash.getBuilder().getClass() == SHA256.class;

To generate a SHA-256 hash of large data use:

    SHA256HashBuilder b = new SHA256HashBuilder();
    b.update("Hello");
    b.update(" World");
    SHA256Hash hash = b.hash();
    b.reset();
    SHA256Hash hash2 = b.hash("Hello World");
    assert hash.equals(hash2);

To generate a PBKDF2-with-HMAC-SHA-256 hash use:

    PBKDF2WithHmacSHA256HashBuilder b = new PBKDF2WithHmacSHA256HashBuilder(
      SecureHashBuilder.buildSalt( 64 ), /* the salt to vary the hash with. here this salt is securely randomly generated */
      500000 /* the iterations to repeatedly compute the hash to harden against brute-force-dehashing */
    );
    PBKDF2WithHmacSHA256Hash record = b.hash( "my very secure password" );
    // save record.getRaw(), record.getSalt() and record.getIterations() to database
    // recompute the database record from time to time when user logs in with a different salt to protect against data breaches
    // also increase the iterations slightly to keep up with increasing hardware capabilities

To check a second PBKDF2-with-HMAC-SHA-256 hash against a first one (eg for user login) use:

    PBKDF2WithHmacSHA256HashBuilder b = new PBKDF2WithHmacSHA256Hash( saltFromDatabase, iterationsFromDatabase );
    PBKDF2WithHmacSHA256Hash testHash = b.hash( "password user tries to login with" );
    if ( testHash.equals( rawHashFromDatabase ) ) {
        // login correct
    } else {
        // deny access
    }
