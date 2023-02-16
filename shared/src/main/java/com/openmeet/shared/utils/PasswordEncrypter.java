package com.openmeet.shared.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class for password encryption.
 *
 * @author Francesco Granozio
 * @author Angelo Nazzaro
 */
public class PasswordEncrypter {

    /**
     * SHA1 Algorithm implementation.
     *
     * @param password The password to encrypt.
     * @return the encrypted password.
     *
     * @throws NoSuchAlgorithmException if the algorithm is not found.
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     * */
    public static String sha1(String password) throws NoSuchAlgorithmException {

        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();

        for (byte b : result)
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));

        return sb.toString();
    }
}
