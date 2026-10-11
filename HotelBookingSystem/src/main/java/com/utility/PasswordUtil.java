package com.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Lightweight salted SHA-256 hashing so the project has zero extra
 * dependencies. Swap this out for BCrypt/Argon2 in a real production app.
 */
public class PasswordUtil {

    // In production, move this to an environment variable / config file.
    private static final String STATIC_SALT = "HotelStay#2024$Salt";

    private PasswordUtil() { }

    public static String hashPassword(String plainPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest((STATIC_SALT + plainPassword).getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(hashed);
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public static boolean verifyPassword(String plainPassword, String storedHash) {
        String hashOfInput = hashPassword(plainPassword);
        return hashOfInput.equals(storedHash);
    }
}
