package ru.pharus.socnetwork.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Security encryption util for generate and check password hash
 *
 */

public class Security {
    private static final Logger log = LoggerFactory.getLogger(Security.class);
    public static final String SOME_SALT = "123";
    private static final String SHA_1 = "SHA1";

    /**
     * Checking for a match password and encoded with salt password hash
     *
     * @param password - verifiable password
     * @param salt - set additional 'salt' parameter
     * @param hash - encoded password hash
     * @return boolean - true if password matches the hash, false if is not match
     */
    public static boolean checkHash(String password, String salt, String hash) {
        log.info("Checking hash for equals password");
        return !(null == hash || null == password) && hash.equals(generateHash(password, salt));
    }

    /**
     * Generate encoded password hash,
     * By default encrypt algorithm is SHA1
     *
     * @param password string to be encoded
     * @param salt addition parameter, may be empty string ""
     * @return encoded password hash
     */

    public static String generateHash(String password, String salt){
        log.info("Generating hash for password");
        if (null == password) return null;

        // TODO: 16.03.2017 java.security.SecureRandom add this function for random salt generate
        StringBuffer sb = new StringBuffer();
            try{
                MessageDigest mDigest = MessageDigest.getInstance(SHA_1);
                byte[] result = mDigest.digest((password + salt).getBytes());

                for (int i = 0; i < result.length; i++) {
                    sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
                }
            }catch (NoSuchAlgorithmException e){
                log.error("Security SHA1 algorithm not found", e);
            }
        return sb.toString();
    }
}
