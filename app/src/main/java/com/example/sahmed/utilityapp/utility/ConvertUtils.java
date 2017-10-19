package com.example.sahmed.utilityapp.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.sahmed.utilityapp.utility.constants.MemoryConstants;
import com.example.sahmed.utilityapp.utility.constants.TimeConstants;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by sahmed on 10/18/2017.
 */

public class ConvertUtils {

    private ConvertUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * byteArr to hexString
     * <p> For example: </ p>
     * bytes2HexString (new byte [] {0, (byte) 0xa8}) returns 00A8
     *
     * @param bytes byte array
     * @return hexadecimal uppercase string
     */
    public static String bytes2HexString(final byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >>> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    /**
     * hexString to byteArr
     * <p>
     * <p> For example: </ p>
     * hexString2Bytes ("00A8") returns {0, (byte) 0xA8}
     *
     * @param hexString hexadecimal string
     * @return byte array
     */
    public static byte[] hexString2Bytes(String hexString) {
        if (isSpace(hexString)) return null;
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }

    /**
     * hexChar int int
     *
     * @param hexChar hex single byte
     * @return 0..15
     */
    private static int hex2Dec(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * charArr to byteArr
     *
     * @param chars character array
     * @return byte array
     */
    public static byte[] chars2Bytes(final char[] chars) {
        if (chars == null || chars.length <= 0) return null;
        int len = chars.length;
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = (byte) (chars[i]);
        }
        return bytes;
    }

    /**
     * byteArr to charArr
     *
     * @param bytes byte array
     * @return character array
     */
    public static char[] bytes2Chars(final byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] chars = new char[len];
        for (int i = 0; i < len; i++) {
            chars[i] = (char) (bytes[i] & 0xff);
        }
        return chars;
    }

    /**
     * The amount of memory in units of units
     *
     * @param memorySize size
     * @param unit       unit type
     *                   <ul>
     *                   <li> {@ link MemoryConstants # BYTE}: bytes </li>
     *                   <li> {@ link MemoryConstants # KB}: kilobytes </li>
     *                   <li> {@ link MemoryConstants # MB}: megabytes </li>
     *                   <li> {@ link MemoryConstants # GB}: GB </li>
     *                   </ul>
     * @return bytes
     */
    public static long memorySize2Byte(final long memorySize, @MemoryConstants.Unit final int unit) {
        if (memorySize < 0) return -1;
        return memorySize * unit;
    }

    /**
     * The number of bytes turns to the memory size in units of units
     *
     * @param byteNum bytes
     * @param unit    unit type
     *                <ul>
     *                <li> {@ link MemoryConstants # BYTE}: bytes </li>
     *                <li> {@ link MemoryConstants # KB}: kilobytes </li>
     *                <li> {@ link MemoryConstants # MB}: megabytes </li>
     *                <li> {@ link MemoryConstants # GB}: GB </li>
     *                </ul>
     * @return is the unit size
     */
    public static double byte2MemorySize(final long byteNum, @MemoryConstants.Unit final int unit) {
        if (byteNum < 0) return -1;
        return (double) byteNum / unit;
    }

    /**
     * The number of bytes to the appropriate memory size
     * <p>
     *  <p> keep 3 decimal places </ p>
     *  @param byteNum bytes
     *  @return appropriate memory size
     */
    @SuppressLint("DefaultLocale")
    public static String byte2FitMemorySize(final long byteNum) {
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        } else if (byteNum < MemoryConstants.KB) {
            return String.format("%.3fB", (double) byteNum);
        } else if (byteNum < MemoryConstants.MB) {
            return String.format("%.3fKB", (double) byteNum / MemoryConstants.KB);
        } else if (byteNum < MemoryConstants.GB) {
            return String.format("%.3fMB", (double) byteNum / MemoryConstants.MB);
        } else {
            return String.format("%.3fGB", (double) byteNum / MemoryConstants.GB);
        }
    }

    /**
     * Turns the timestamp in units of time
     *
     * @param timeSpan millisecond timestamp
     * @param unit     unit type
     *                 <ul>
     *                 <li> {@ link TimeConstants # MSEC}: milliseconds </li>
     *                 <li> {@ link TimeConstants # SEC}: seconds </li>
     *                 <li> {@ link TimeConstants # MIN}: points </li>
     *                 <li> {@ link TimeConstants # HOUR}: hours </li>
     *                 <li> {@ link TimeConstants # DAY}: days </li>
     *                 </ul>
     * @return millisecond timestamp
     */
    public static long timeSpan2Millis(final long timeSpan, @TimeConstants.Unit final int unit) {
        return timeSpan * unit;
    }

    /**
     * Millisecond timestamps turn in units of time
     *
     * @param millis millisecond timestamp
     * @param unit   unit type
     *                <ul>
     *                <li> {@ link TimeConstants # MSEC}: milliseconds </li>
     *               <li> {@ link TimeConstants # SEC}: seconds </li>
     *               <li> {@ link TimeConstants # MIN}: points </li>
     *               <li> {@ link TimeConstants # HOUR}: hours </li>
     *               <li> {@ link TimeConstants # DAY}: days </li>
     *               </ul>
     *                 @return The length of time in units of units
     */
    public static long millis2TimeSpan(final long millis, @TimeConstants.Unit final int unit) {
        return millis / unit;
    }

    /**
     * Millisecond timestamps for the appropriate length of time
     *
     * @param millis    millisecond timestamp
     *                  <p> is less than or equal to 0, returns null </ p>
     * @param precision <ul>
     *                  <li> precision = 0, return null </ li>
     *                  <li> precision = 1, return day </ li>
     *                  <li> precision = 2, return day and hour </ li>
     *                  <li> precision = 3, return days, hours and minutes </ li>
     *                  <li> precision = 4, return days, hours, minutes and seconds </ li>
     *                  <li> precision & gt; = 5, return days, hours, minutes, seconds and milliseconds </ li>
     *                  </ ul>
     * @return appropriate length of time
     */
    @SuppressLint("DefaultLocale")
    public static String millis2FitTimeSpan(long millis, int precision) {
        if (millis <= 0 || precision <= 0) return null;
        StringBuilder sb = new StringBuilder();
        String[] units = {"day", "hour", "minute", "second", "millisecond"};
        int[] unitLen = {86400000, 3600000, 60000, 1000, 1};
        precision = Math.min(precision, 5);
        for (int i = 0; i < precision; i++) {
            if (millis >= unitLen[i]) {
                long mode = millis / unitLen[i];
                millis -= mode * unitLen[i];
                sb.append(mode).append(units[i]);
            }
        }
        return sb.toString();
    }

    /**
     * bytes to bits
     *
     * @param bytes byte array
     * @return bits
     */
    public static String bytes2Bits(final byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            for (int j = 7; j >= 0; --j) {
                sb.append(((aByte >> j) & 0x01) == 0 ? '0' : '1');
            }
        }
        return sb.toString();
    }

    /**
     * bits to bytes
     *
     * @param bits binary
     * @return bytes
     */
    public static byte[] bits2Bytes(String bits) {
        int lenMod = bits.length() % 8;
        int byteLen = bits.length() / 8;
        // not a multiple of 8
        if (lenMod != 0) {
            for (int i = lenMod; i < 8; i++) {
                bits = "0" + bits;
            }
            byteLen++;
        }
        byte[] bytes = new byte[byteLen];
        for (int i = 0; i < byteLen; ++i) {
            for (int j = 0; j < 8; ++j) {
                bytes[i] <<= 1;
                bytes[i] |= bits.charAt(i * 8 + j) - '0';
            }
        }
        return bytes;
    }

    /**
     * inputStream to outputStream
     *
     * @param is the input stream
     * @return outputStream subclass
     */
    public static ByteArrayOutputStream input2OutputStream(final InputStream is) {
        if (is == null) return null;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] b = new byte[MemoryConstants.KB];
            int len;
            while ((len = is.read(b, 0, MemoryConstants.KB)) != -1) {
                os.write(b, 0, len);
            }
            return os;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
               CloseUtils.closeIO(is);
        }
    }

    /**
     * outputStream to inputStream
     *
     * @param out the output stream
     * @return inputStream subclass
     */
    public ByteArrayInputStream output2InputStream(final OutputStream out) {
        if (out == null) return null;
        return new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());
    }

    /**
     * inputStream to byteArr
     *
     * @param is the input stream
     * @return byte array
     */
    public static byte[] inputStream2Bytes(final InputStream is) {
        if (is == null) return null;
        return input2OutputStream(is).toByteArray();
    }

    /**
     * byteArr to inputStream
     * <p>
     *  @param bytes byte array
     *  @return input stream
     */
    public static InputStream bytes2InputStream(final byte[] bytes) {
        if (bytes == null || bytes.length <= 0) return null;
        return new ByteArrayInputStream(bytes);
    }

    /**
     * outputStream to byteArr
     *
     * @param out the output stream
     * @return byte array
     */
    public static byte[] outputStream2Bytes(final OutputStream out) {
        if (out == null) return null;
        return ((ByteArrayOutputStream) out).toByteArray();
    }

    /**
     * byteArr to outputStream
     *
     * @param bytes byte array
     * @return byte array
     */
    public static OutputStream bytes2OutputStream(final byte[] bytes) {
        if (bytes == null || bytes.length <= 0) return null;
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            os.write(bytes);
            return os;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(os);
        }
    }

    /**
     * InputStream turn string by encoding
     *
     * @param is          the input stream
     * @param charsetName encoding format
     * @return string
     */
    public static String inputStream2String(final InputStream is, final String charsetName) {
        if (is == null || isSpace(charsetName)) return null;
        try {
            return new String(inputStream2Bytes(is), charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * string to inputStream by code
     *
     * @param string      string
     * @param charsetName encoding format
     * @return input stream
     */
    public static InputStream string2InputStream(final String string, final String charsetName) {
        if (string == null || isSpace(charsetName)) return null;
        try {
            return new ByteArrayInputStream(string.getBytes(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * outputStream
     *
     * @param out         the output stream
     * @param charsetName encoding format
     * @return string
     */
    public static String outputStream2String(final OutputStream out, final String charsetName) {
        if (out == null || isSpace(charsetName)) return null;
        try {
            return new String(outputStream2Bytes(out), charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * string to outputStream by code
     *
     * @param string      string
     * @param charsetName encoding format
     * @return input stream
     */
    public static OutputStream string2OutputStream(final String string, final String charsetName) {
        if (string == null || isSpace(charsetName)) return null;
        try {
            return bytes2OutputStream(string.getBytes(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * bitmap to byteArr
     *
     * @param bitmap bitmap object
     * @param format
     * @return byte array
     */
    public static byte[] bitmap2Bytes(final Bitmap bitmap, final Bitmap.CompressFormat format) {
        if (bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, 100, baos);
        return baos.toByteArray();
    }

    /**
     * byteArr to bitmap
     *
     * @param bytes byte array
     * @return bitmap
     */
    public static Bitmap bytes2Bitmap(final byte[] bytes) {
        return (bytes == null || bytes.length == 0) ? null : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * drawable to bitmap
     *
     * @param drawable drawable object
     * @return bitmap
     */
    public static Bitmap drawable2Bitmap(final Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        Bitmap bitmap;
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1,
                    drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * bitmap to drawable
     *
     * @param bitmap  bitmap object
     * @param context context of application
     * @return drawable
     */
    public static Drawable bitmap2Drawable(final Bitmap bitmap, Context context) {
        return bitmap == null ? null : new BitmapDrawable(context.getResources(), bitmap);
    }

    /**
     * drawable to byteArr
     *
     * @param drawable drawable object
     * @param format
     * @return byte array
     */
    public static byte[] drawable2Bytes(final Drawable drawable, final Bitmap.CompressFormat format) {
        return drawable == null ? null : bitmap2Bytes(drawable2Bitmap(drawable), format);
    }

    /**
     * byteArr to drawable
     *
     * @param bytes byte array
     * @return drawable
     */
    public static Drawable bytes2Drawable(final byte[] bytes, Context context) {
        return bytes == null ? null : bitmap2Drawable(bytes2Bitmap(bytes), context);
    }

    /**
     * view to Bitmap
     *  @param view view
     *  @return bitmap
     */
    public static Bitmap view2Bitmap(final View view) {
        if (view == null) return null;
        Bitmap ret = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(ret);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return ret;
    }

    /**
     * dp to px
     *
     * @param dpValue dp value
     * @param context context of application
     * @return px value
     */
    public static int dp2px(final float dpValue, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px to dp
     *
     * @param pxValue px value
     * @param context context of application
     * @return dp value
     */
    public static int px2dp(final float pxValue, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp to px
     *
     * @param spValue sp value
     * @param context context of application
     * @return px value
     */
    public static int sp2px(final float spValue, Context context) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px to sp
     *
     * @param pxValue px value
     * @param context context of application
     * @return sp value
     */
    public static int px2sp(final float pxValue, Context context) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * Determines whether the string is null or blank
     *
     * @param s to be verified by the string
     * @return {@code true}: null or all blank characters {@code false}: not null and not all blank characters
     */
    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
