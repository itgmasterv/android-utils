package com.example.sahmed.utilityapp.regex;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/02
 *     desc  : 正则相关工具类
 * </pre>
 */
public final class RegexUtils {

    private RegexUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    ///////////////////////////////////////////////////////////////////////////
    // If u want more please visit http://toutiao.com/i6231678548520731137
    //////// ///////////////////////////////////////////////////////////////////

    /**
     * Verify phone number (simple)
     *
     * @param input The text to be verified
     * @return {@code true}: match<br>{@code false}: does not match
     */
    public static boolean isMobileSimple(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_MOBILE_SIMPLE, input);
    }

    /**
     * Verify phone number (exact)
     *
     * @param input The text to be verified
     * @return {@code true}: match<br>{@code false}: does not match
     */
    public static boolean isMobileExact(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_MOBILE_EXACT, input);
    }

    /**
     * Verify the phone number
     *
     * @param input The text to be verified
     * @return {@code true}: match<br>{@code false}: does not match
     */
    public static boolean isTel(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_TEL, input);
    }

    /**
     * Verify the ID number 15 bits
     *
     * @param input The text to be verified
     * @return {@code true}: match<br>{@code false}: does not match
     */
    public static boolean isIDCard15(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_ID_CARD15, input);
    }

    /**
     * Verify that the ID number is 18 digits
     *
     * @param input The text to be verified
     * @return {@code true}: match<br>{@code false}: does not match
     */
    public static boolean isIDCard18(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_ID_CARD18, input);
    }

    /**
     * Verify the mailbox
     *
     * @param input The text to be verified
     * @return {@code true}: match<br>{@code false}: does not match
     */
    public static boolean isEmail(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_EMAIL, input);
    }

    /**
     * Verify the URL
     *
     * @param input The text to be verified
     * @return {@code true}: match<br>{@code false}: does not match
     */
    public static boolean isURL(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_URL, input);
    }

    /**
     * Validate Chinese characters
     *
     * @param input The text to be verified
     * @return {@code true}: match<br>{@code false}: does not match
     */
    public static boolean isZh(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_ZH, input);
    }

    /**
     * Verify the user name
     * <p>The range is a-z,A-Z,0-9,"_",Chinese character，Can not"_"At the end, the username must be 6-20位</p>
     *
     * @param input The text to be verified
     * @return {@code true}: match<br>{@code false}: does not match
     */
    public static boolean isUsername(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_USERNAME, input);
    }

    /**
     *Verification yyyy-MM-dd format date verification
     *
     * @param input The text to be verified
     * @return {@code true}: match<br>{@code false}: does not match
     */
    public static boolean isDate(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_DATE, input);
    }

    /**
     * Verify the IP address
     *
     * @param input The text to be verified
     * @return {@code true}: match<br>{@code false}: does not match
     */
    public static boolean isIP(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_IP, input);
    }

    /**
     * To determine whether to match the regular
     *
     * @param regex Regular expression
     * @param input The text to be verified
     * @return {@code true}: match<br>{@code false}: does not match
     */
    public static boolean isMatch(final String regex, final CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

    /**
     * Get the regular match
     *
     * @param regex Regular expression
     * @param input The string to be verified
     * @return Regularly matched parts
     */
    public static List<String> getMatches(final String regex, final CharSequence input) {
        if (input == null) return null;
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

    /**
     * Get a regular match group
     *
     * @param input The string to be grouped
     * @param regex Regular expression
     * @return Regular match grouping
     */
    public static String[] getSplits(final String input, final String regex) {
        if (input == null) return null;
        return input.split(regex);
    }

    /**
     * Replace the first part of the regular match
     *
     * @param input       The string to be replaced
     * @param regex       Regular expression
     * @param replacement Alternator
     * @return Replace the first part of the regular match
     */
    public static String getReplaceFirst(final String input, final String regex, final String replacement) {
        if (input == null) return null;
        return Pattern.compile(regex).matcher(input).replaceFirst(replacement);
    }

    /**
     * Replace all the regular matches
     *
     * @param input       The string to be replaced
     * @param regex       Regular expression
     * @param replacement Alternator
     * @return Replace all the regular matches
     */
    public static String getReplaceAll(final String input, final String regex, final String replacement) {
        if (input == null) return null;
        return Pattern.compile(regex).matcher(input).replaceAll(replacement);
    }
}
