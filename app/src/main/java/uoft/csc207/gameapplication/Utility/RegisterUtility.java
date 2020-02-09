package uoft.csc207.gameapplication.Utility;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class RegisterUtility {
    /**
     * The email regex pattern that is used to determine if this email is valid.
     */
    public static final String emailRegex = "^[a-zA-Z0-9]+([-_+.][a-zA-Z0-9]+)*" +
            "@([a-zA-Z0-9]+)([-.][a-zA-Z0-9]+)*\\.[a-zA-Z]{2,7}$";
  /**
   * passwordRegex pattern that checks to see if the length is at least 8 character has a *
   * capital, lowercase, a number, and the symbols @#$%-_=+!^&*\
   */
  public static final String passwordRegex =
      "^\\.*(?=.*[0-9])\\.*(?=.*[a-z])"
          + "\\.*(?=.*[A-Z])\\.*(?=.*[@#$%-_=+!^&*\\\\])\\.*(?=\\S+$).{8,}$";

    /**
     * In this project we will be using mostly SHA-256 hashing algorithm.
     * @param value the unhashed string
     * @param hashingAlgorithm a valid string hashing algorithm from the java security message
     *                         digest
     * @return the hash created by the valid hashingAlgorithm
     */
    public static String hash(String value, String hashingAlgorithm) {
        try {
            String valueHash = "";
            MessageDigest mDigest = MessageDigest.getInstance(hashingAlgorithm);
            byte[] hash = mDigest.digest(value.getBytes(StandardCharsets.UTF_8));

            for (byte b : hash) {
                valueHash += String.format("%02x", b);
            }
            return valueHash;
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * returns true if this email conforms to the emailRegex pattern
     * @param email a string for the email
     * @return whether the email conforms to the email regex pattern
     */
    public static boolean validEmail(String email) {
        Pattern regexPattern = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return regexPattern.matcher(email).matches();
    }

    /**
     * strongPassword will return true if the length is at least 8 character has a
     * capital, lowercase, a number, and the symbols @#$%-_=+!^&*\
     * @param password the users current password
     * @return whether the password conforms to the regex pattern passwordRegex
     */
    public static boolean strongPassword(String password) {
        Pattern regexPattern = Pattern.compile(passwordRegex);
        if (password == null)
            return false;
        return regexPattern.matcher(password).matches();
    }
}
