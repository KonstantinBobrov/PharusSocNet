package ru.pharus.socnetwork.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** Generating strings in SHA1 hash strings
 *
 */

public class Security {
    static final Logger log = LoggerFactory.getLogger(Security.class);

    public static boolean checkHash(String password, String salt, String hash){
        log.info("Checking hash for equals password");
        if (null == hash) return false;
        return hash.equals(generateHash(password,salt));
    }

    public static String generateHash(String password, String salt){
        log.info("Generating hash for password");
        // TODO: 16.03.2017 java.security.SecureRandom add this function for random salt generate
        StringBuffer sb = new StringBuffer();
            try{
                MessageDigest mDigest = MessageDigest.getInstance("SHA1");
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
