package com.example.sahmed.utilityapp.utility;

/**
 * Created by sahmed on 10/17/2017.
 */

public class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Determines whether the string is null or the length is 0
     *
     * @param s to be verified by the string
     * @return {@code true}: empty {@code false}: not empty     
     */
    public static boolean isEmpty(final CharSequence s) {
        return s == null || s.length() == 0;
    }


    /**
     * Determines whether the string is null or all spaces
     *
     * @param s to be verified by the string
     * @return {@code true}: null or full space <br> {@code false}: not null and not all spaces    
     */
    public static boolean isTrimEmpty(final String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * Determines whether the string is null or blank
     *
     * @param s to be verified by the string
     * @return {@code true}: null or all blank characters {@code false}: not null and not all blank characters    
     */
    public static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    /**
     * Determine whether the two strings are equal
     *
     * @param a Check the string a
     * @param b to be verified string b
     * @return {@code true}: Equal - {@code false}: Not equal     
     */
    public static boolean equals(final CharSequence a, final CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Determine whether the two strings ignore the case is equal
     *
     * @param a Check the string a
     * @param b to be verified string b
     * @return {@code true}: Equal - {@code false}: Not equal    
     */
    public static boolean equalsIgnoreCase(final String a, final String b) {
        return a == null ? b == null : a.equalsIgnoreCase(b);
    }

    /**
     * null to a string of length 0
     *
     * @param s pending string
     * @return s is null to a length of 0 string, otherwise it will not change
     */
    public static String null2Length0(final String s) {
        return s == null ? "" : s;
    }

    /**
     * Returns the length of the string
     *
     * @param s string
     * @return null returns 0, the other returns its own length
     */
    public static int length(final CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * First letter capitalized
     *      
     *
     * @param s pending string
     * @return first letter uppercase character string
     */
    public static String upperFirstLetter(final String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * First letter lowercase
     *
     * @param s pending string
     * @return first letter lowercase string
     */
    public static String lowerFirstLetter(final String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * Reverses the string
     *     
     *
     * @param s to reverse the string
     * @return reverses the string
     */
    public static String reverse(final String s) {
        int len = length(s);
        if (len <= 1) return s;
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }


}
