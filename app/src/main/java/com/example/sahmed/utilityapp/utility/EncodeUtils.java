package com.example.sahmed.utilityapp.utility;

import android.os.Build;
import android.text.Html;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by mehab on 10/19/2017.
 */

public class EncodeUtils {

    private EncodeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * url Encode
     * <p>If you want to specify your own character set, you can use it{@link #urlEncode(String input, String charset)}method</p>
     *
     * @param input The character to be encoded
     * @return A string encoded as UTF-8
     */
    public static String urlEncode(final String input) {
        return urlEncode(input, "UTF-8");
    }

    /**
     * URL encoding
     * <p>if the system does not support the specified encoding character set, the input is returned as is</p>
     *
     * @param input   The character to be encoded
     * @param charset character set
     * @return A string encoded as a character set
     */
    public static String urlEncode(final String input, final String charset) {
        try {
            return URLEncoder.encode(input, charset);
        } catch (UnsupportedEncodingException e) {
            return input;
        }
    }

    /**
     * URL decoding
     * <p>If you want to specify your own character set, you can use it {@link #urlDecode(String input, String charset)}method</p>
     *
     * @param input The string to be decoded
     * @return URL The decoded string
     */
    public static String urlDecode(final String input) {
        return urlDecode(input, "UTF-8");
    }

    /**
     * URL decoding
     * <p>If the system does not support the specified decoding character set, the input will be returned as is</p>
     *
     * @param input   The string to be decoded
     * @param charset character set
     * @return The URL is decoded as a string of the specified character set
     */
    public static String urlDecode(final String input, final String charset) {
        try {
            return URLDecoder.decode(input, charset);
        } catch (UnsupportedEncodingException e) {
            return input;
        }
    }

    /**
     * Base64 encoding
     *
     * @param input The string to be encoded
     * @return Base64 encoded string
     */
    public static byte[] base64Encode(final String input) {
        return base64Encode(input.getBytes());
    }

    /**
     * Base64 encoding
     *
     * @param input TThe array of bytes to be encoded
     * @return Base64 encoded string
     */
    public static byte[] base64Encode(final byte[] input) {
        return Base64.encode(input, Base64.NO_WRAP);
    }

    /**
     * Base64 encoding
     *
     * @param input The array of bytes to be encoded
     * @return Base64 encoded string
     */
    public static String base64Encode2String(final byte[] input) {
        return Base64.encodeToString(input, Base64.NO_WRAP);
    }

    /**
     * Base64 encoding
     *
     * @param input The string to be decoded
     * @return Base64 decoded string
     */
    public static byte[] base64Decode(final String input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }

    /**
     * Base64 Decoding
     *
     * @param input The array of bytes to be decoded
     * @return Base64 decoded string
     */
    public static byte[] base64Decode(final byte[] input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }

    /**
     * Base64URL security code
     * <p>Make the URL illegal characters in Base64characters</p>
     *
     * @param input The URL illegal characters in Base64characters
     * @return Base64URL Secure encoded string
     */
    public static byte[] base64UrlSafeEncode(final String input) {
        return Base64.encode(input.getBytes(), Base64.URL_SAFE);
    }

    /**
     * Html encoding
     *
     * @param input Html encoded string
     * @return Html encoded string
     */
    public static String htmlEncode(final CharSequence input) {
        StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0, len = input.length(); i < len; i++) {
            c = input.charAt(i);
            switch (c) {
                case '<':
                    sb.append("&lt;"); //$NON-NLS-1$
                    break;
                case '>':
                    sb.append("&gt;"); //$NON-NLS-1$
                    break;
                case '&':
                    sb.append("&amp;"); //$NON-NLS-1$
                    break;
                case '\'':
                    //http://www.w3.org/TR/xhtml1
                    // The named character reference &apos; (the apostrophe, U+0027) was
                    // introduced in XML 1.0 but does not appear in HTML. Authors should
                    // therefore use &#39; instead of &apos; to work as expected in HTML 4
                    // user agents.
                    sb.append("&#39;"); //$NON-NLS-1$
                    break;
                case '"':
                    sb.append("&quot;"); //$NON-NLS-1$
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Html decoding
     *
     * @param input The string to be decoded
     * @return Html decoded string
     */
    @SuppressWarnings("deprecation")
    public static CharSequence htmlDecode(final String input) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(input);
        }
    }
}
