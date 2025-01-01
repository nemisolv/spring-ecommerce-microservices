package net.nemisolv.lib.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoUtil {

    /**
     * Hashes a message using SHA-256 algorithm.
     *
     * @param message The message to hash.
     * @return The SHA-256 hash of the message as a hex string.
     * @throws NoSuchAlgorithmException If SHA-256 algorithm is not available.
     */
    public static String sha256Hash(String message) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hash = digest.digest(message.getBytes(StandardCharsets.UTF_8));

        // Convert byte array to hex string
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = String.format("%02x", b);
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
