package com.example.sahmed.utilityapp.utility;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by mehab on 10/19/2017.
 */

public class EncryptUtils {
    private EncryptUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    ///////////////////////////////////////////////////////////////////////////
    // Hash encryption related
    ///////////////////////////////////////////////////////////////////////////

    /**
     * MD2 encryption (Message-Digest Algorithm)
     *
     * @param data Plaintext string
     * @return 16 hexadecimal cipher text
     */
    public static String encryptMD2ToString(final String data) {
        return encryptMD2ToString(data.getBytes());
    }

    /**
     * MD2 encryption (Message-Digest Algorithm)
     *
     * @param data Array of plaintext bytes
     * @return 16 hexadecimal cipher text
     */
    public static String encryptMD2ToString(final byte[] data) {
        return bytes2HexString(encryptMD2(data));
    }

    /**
     * MD2 encryption (Message-Digest Algorithm)
     *
     * @param data Array of plaintext bytes
     * @return 16 hexadecimal cipher text bytes
     */
    public static byte[] encryptMD2(final byte[] data) {
        return hashTemplate(data, "MD2");
    }

    /**
     * MD5 encryption
     *
     * @param data Plaintext string
     * @return 16 hexadecimal cipher text
     */
    public static String encryptMD5ToString(final String data) {
        return encryptMD5ToString(data.getBytes());
    }

    /**
     * MD5 encryption
     *
     * @param data Plaintext string
     * @param salt salt
     * @return 16 hexadecimal cipher text
     */
    public static String encryptMD5ToString(final String data, final String salt) {
        return bytes2HexString(encryptMD5((data + salt).getBytes()));
    }

    /**
     *  MD5 encryption
     *
     * @param data Plaintext string
     * @return 16 hexadecimal cipher text
     */
    public static String encryptMD5ToString(final byte[] data) {
        return bytes2HexString(encryptMD5(data));
    }

    /**
     * MD5 encryption
     *
     * @param data Array of plaintext bytes
     * @param salt Salt byte array
     * @return Hexadecimal salt
     */
    public static String encryptMD5ToString(final byte[] data, final byte[] salt) {
        if (data == null || salt == null) return null;
        byte[] dataSalt = new byte[data.length + salt.length];
        System.arraycopy(data, 0, dataSalt, 0, data.length);
        System.arraycopy(salt, 0, dataSalt, data.length, salt.length);
        return bytes2HexString(encryptMD5(dataSalt));
    }

    /**
     * MD5 encryption
     *
     * @param data Array of plain text bytes
     * @return An array of cipher text bytes
     */
    public static byte[] encryptMD5(final byte[] data) {
        return hashTemplate(data, "MD5");
    }

    /**
     * MD5 encrypted file
     *
     * @param filePath file Path
     * @return File hexadecimal cipher text
     */
    public static String encryptMD5File2String(final String filePath) {
        File file = isSpace(filePath) ? null : new File(filePath);
        return encryptMD5File2String(file);
    }

    /**
     * MD5 encrypted file
     *
     * @param filePath file Path
     * @return The MD5 checksum of the file
     */
    public static byte[] encryptMD5File(final String filePath) {
        File file = isSpace(filePath) ? null : new File(filePath);
        return encryptMD5File(file);
    }

    /**
     * MD5 encrypted file
     *
     * @param file File
     * @return File hexadecimal ciphertext
     */
    public static String encryptMD5File2String(final File file) {
        return bytes2HexString(encryptMD5File(file));
    }

    /**
     * MD5 encrypted file
     *
     * @param file File
     * @return The MD5 checksum of the file
     */
    public static byte[] encryptMD5File(final File file) {
        if (file == null) return null;
        FileInputStream fis = null;
        DigestInputStream digestInputStream;
        try {
            fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            digestInputStream = new DigestInputStream(fis, md);
            byte[] buffer = new byte[256 * 1024];
            while (true) {
                if (!(digestInputStream.read(buffer) > 0)) break;
            }
            md = digestInputStream.getMessageDigest();
            return md.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(fis);
        }
    }

    /**
     * SHA1 encryption
     *
     * @param data Plain text string
     * @return 16 hexadecimal cipher text
     */
    public static String encryptSHA1ToString(final String data) {
        return encryptSHA1ToString(data.getBytes());
    }

    /**
     * SHA1 encryption
     *
     * @param data Array of plain text bytes
     * @return 16 hexadecimal cipher text
     */
    public static String encryptSHA1ToString(final byte[] data) {
        return bytes2HexString(encryptSHA1(data));
    }

    /**
     * SHA1 encryption
     *
     * @param data Array of plaintext bytes
     * @return An array of cipher text bytes
     */
    public static byte[] encryptSHA1(final byte[] data) {
        return hashTemplate(data, "SHA1");
    }

    /**
     * SHA224 encryption
     *
     * @param data Plain text string
     * @return 16 hexadecimal cipher text
     */
    public static String encryptSHA224ToString(final String data) {
        return encryptSHA224ToString(data.getBytes());
    }

    /**
     * SHA224 encryption
     *
     * @param data Plain text  byte array
     * @return 16 hexadecimal cipher text
     */
    public static String encryptSHA224ToString(final byte[] data) {
        return bytes2HexString(encryptSHA224(data));
    }

    /**
     * SHA224 encryption
     *
     * @param data Array of plain text bytes
     * @return An array of cipher text bytes
     */
    public static byte[] encryptSHA224(final byte[] data) {
        return hashTemplate(data, "SHA224");
    }

    /**
     * SHA256 encryption
     *
     * @param data Plaintext string
     * @return 16 hexadecimal cipher text
     */
    public static String encryptSHA256ToString(final String data) {
        return encryptSHA256ToString(data.getBytes());
    }

    /**
     * SHA256 encryption
     *
     * @param data Array of plaintext bytes
     * @return 16 hexadecimal cipher text
     */
    public static String encryptSHA256ToString(final byte[] data) {
        return bytes2HexString(encryptSHA256(data));
    }

    /**
     * SHA256 encryption
     *
     * @param data Array of plaintext bytes
     * @return An array of cipher text bytes
     */
    public static byte[] encryptSHA256(final byte[] data) {
        return hashTemplate(data, "SHA256");
    }

    /**
     * SHA384 encryption
     *
     * @param data Plaintext string
     * @return 16 hexadecimal ciphertext
     */
    public static String encryptSHA384ToString(final String data) {
        return encryptSHA384ToString(data.getBytes());
    }

    /**
     * SHA384 encryption
     *
     * @param data Array of plaintext bytes
     * @return 16 hexadecimal cipher text
     */
    public static String encryptSHA384ToString(final byte[] data) {
        return bytes2HexString(encryptSHA384(data));
    }

    /**
     * SHA384 encryption
     *
     * @param data Array of plaintext bytes
     * @return An array of ciphertext bytes
     */
    public static byte[] encryptSHA384(final byte[] data) {
        return hashTemplate(data, "SHA384");
    }

    /**
     * SHA512 encryption
     *
     * @param data Plaintext string
     * @return 16 hexadecimal ciphertext
     */
    public static String encryptSHA512ToString(final String data) {
        return encryptSHA512ToString(data.getBytes());
    }

    /**
     * SHA512 encryption
     *
     * @param data Array of plaintext bytes
     * @return 16 hexadecimal ciphertext
     */
    public static String encryptSHA512ToString(final byte[] data) {
        return bytes2HexString(encryptSHA512(data));
    }

    /**
     * SHA512 encryption
     *
     * @param data Array of plaintext bytes
     * @return An array of ciphertext bytes
     */
    public static byte[] encryptSHA512(final byte[] data) {
        return hashTemplate(data, "SHA512");
    }

    /**
     * hash encryption template
     *
     * @param data      data
     * @param algorithm Encryption Algorithm
     * @return An array of ciphertext bytes
     */
    private static byte[] hashTemplate(final byte[] data, final String algorithm) {
        if (data == null || data.length <= 0) return null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * HmacMD5 encryption
     *
     * @param data Plaintext string
     * @param key  key
     * @return 16 hexadecimal ciphertext
     */
    public static String encryptHmacMD5ToString(final String data, final String key) {
        return encryptHmacMD5ToString(data.getBytes(), key.getBytes());
    }

    /**
     * Hmac MD5 encryption
     *
     * @param data Array of plaintext bytes
     * @param key  key
     * @return 16 hexadecimal ciphertext
     */
    public static String encryptHmacMD5ToString(final byte[] data, final byte[] key) {
        return bytes2HexString(encryptHmacMD5(data, key));
    }

    /**
     * Hmac MD5 encryption
     *
     * @param data Array of plaintext bytes
     * @param key  key
     * @return Array of plaintext bytes
     */
    public static byte[] encryptHmacMD5(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacMD5");
    }

    /**
     * Hmac SHA1 encryption
     *
     * @param data Plaintext string
     * @param key  key
     * @return 16 hexadecimal ciphertext
     */
    public static String encryptHmacSHA1ToString(final String data, final String key) {
        return encryptHmacSHA1ToString(data.getBytes(), key.getBytes());
    }

    /**
     * Hmac SHA1 encryption
     *
     * @param data Array of plaintext bytes
     * @param key  key
     * @return 16 hexadecimal ciphertext
     */
    public static String encryptHmacSHA1ToString(final byte[] data, final byte[] key) {
        return bytes2HexString(encryptHmacSHA1(data, key));
    }

    /**
     * Hmac SHA1 encryption
     *
     * @param data Array of plaintext bytes
     * @param key  key
     * @return An array of ciphertext bytes
     */
    public static byte[] encryptHmacSHA1(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacSHA1");
    }

    /**
     * HmacSHA224 encryption
     *
     * @param data Plaintext string
     * @param key  key
     * @return 16 hexadecimal ciphertext
     */
    public static String encryptHmacSHA224ToString(final String data, final String key) {
        return encryptHmacSHA224ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA224 encryption
     *
     * @param data Array of plaintext bytes
     * @param key  key
     * @return 16 hexadecimal ciphertext
     */
    public static String encryptHmacSHA224ToString(final byte[] data, final byte[] key) {
        return bytes2HexString(encryptHmacSHA224(data, key));
    }

    /**
     * Hmac SHA224 encryption (keyed-hash message authentication code (HMAC))
     *
     * @param data Array of plaintext bytes
     * @param key  key
     * @return An array of ciphertext bytes
     */
    public static byte[] encryptHmacSHA224(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacSHA224");
    }

    /**
     * Hmac SHA224 keyed-hash message authentication code (HMAC)
     *
     * @param data Plaintext string
     * @param key  key
     * @return 16 hexadecimal ciphertext
     */
    public static String encryptHmacSHA256ToString(final String data, final String key) {
        return encryptHmacSHA256ToString(data.getBytes(), key.getBytes());
    }

    /**
     * Hmac SHA256 encryption
     *
     * @param data Array of plaintext bytes
     * @param key  key
     * @return 16 hexadecimal ciphertext
     */
    public static String encryptHmacSHA256ToString(final byte[] data, final byte[] key) {
        return bytes2HexString(encryptHmacSHA256(data, key));
    }

    /**
     * Hmac SHA256 encryption
     *
     * @param data Array of plaintext bytes
     * @param key  key
     * @return An array of ciphertext bytes
     */
    public static byte[] encryptHmacSHA256(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacSHA256");
    }

    /**
     * Hmac SHA384 encryption
     *
     * @param data Plaintext string
     * @param key  key
     * @return 16 hexadecimal ciphertext
     */
    public static String encryptHmacSHA384ToString(final String data, final String key) {
        return encryptHmacSHA384ToString(data.getBytes(), key.getBytes());
    }

    /**
     *  Hmac SHA384 encryption
     *
     * @param data Array of plaintext bytes
     * @param key  key
     * @return 16 hexadecimal ciphertext
     */
    public static String encryptHmacSHA384ToString(final byte[] data, final byte[] key) {
        return bytes2HexString(encryptHmacSHA384(data, key));
    }

    /**
     * Hmac SHA384 encryption
     *
     * @param data Array of plaintext bytes
     * @param key  key
     * @return An array of cipher text bytes
     */
    public static byte[] encryptHmacSHA384(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacSHA384");
    }

    /**
     * Hmac SHA512 encryption
     *
     * @param data Plaintext string
     * @param key  key
     * @return 16 hexadecimal ciphertext
     */
    public static String encryptHmacSHA512ToString(final String data, final String key) {
        return encryptHmacSHA512ToString(data.getBytes(), key.getBytes());
    }

    /**
     * Hmac SHA512encryption
     *
     * @param data Array of plaintext bytes
     * @param key  key
     * @return 16 hexadecimal ciphertext
     */
    public static String encryptHmacSHA512ToString(final byte[] data, final byte[] key) {
        return bytes2HexString(encryptHmacSHA512(data, key));
    }

    /**
     * HmacSHA512 encryption
     *
     * @param data Array of plaintext bytes
     * @param key  key
     * @return An array of ciphertext bytes
     */
    public static byte[] encryptHmacSHA512(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacSHA512");
    }

    /**
     * Hmac encryption template
     *
     * @param data      data
     * @param key       key
     * @param algorithm Encryption Algorithm
     * @return An array of ciphertext bytes
     */
    private static byte[] hmacTemplate(final byte[] data, final byte[] key, final String algorithm) {
        if (data == null || data.length == 0 || key == null || key.length == 0) return null;
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(secretKey);
            return mac.doFinal(data);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // DES encryption related
    ///////////////////////////////////////////////////////////////////////////

    /**
     * DES change
     * <p>Algorithm name / encryption mode / padding method</p>
     * <p>The encryption modes are: Electronic Code Mode ECB, Encryption Block Chain Mode CBC, Encryption Feedback Mode CFB, Output Feedback Mode OFB</p>
     * <p>There are stuffing methods：NoPadding、ZerosPadding、PKCS5P adding</p>
     */
    public static        String DES_Transformation = "DES/ECB/NoPadding";
    private static final String DES_Algorithm      = "DES";

    /**
     * AES is encrypted and converted to Base64 encoding
     *
     * @param data data
     * @param key  key
     * @return Base64 cipher text
     */
    public static byte[] encryptDES2Base64(final byte[] data, final byte[] key) {
        return base64Encode(encryptDES(data, key));
    }

    /**
     * AES is encrypted and converted to hexadecimal
     *
     * @param data Clear text
     * @param key  8-byte key
     * @return 16 hexadecimal ciphertext
     */
    public static String encryptDES2HexString(final byte[] data, final byte[] key) {
        return bytes2HexString(encryptDES(data, key));
    }

    /**
     * DES encryption
     *
     * @param data clear text
     * @param key  8-byte key
     * @return Ciphertext
     */
    public static byte[] encryptDES(final byte[] data, final byte[] key) {
        return desTemplate(data, key, DES_Algorithm, DES_Transformation, true);
    }

    /**
     * DES decrypts Base64 encoded ciphertext
     *
     * @param data Base64 encoded ciphertext
     * @param key  8-byte key
     * @return Clear text
     */
    public static byte[] decryptBase64DES(final byte[] data, final byte[] key) {
        return decryptDES(base64Decode(data), key);
    }

    /**
     * DES decrypts the hexadecimal ciphertext
     *
     * @param data 16 hexadecimal ciphertext
     * @param key  8-byte key
     * @return Clear text
     */
    public static byte[] decryptHexStringDES(final String data, final byte[] key) {
        return decryptDES(hexString2Bytes(data), key);
    }

    /**
     * DES encryption
     *
     * @param data Clear text
     * @param key  8-byte key
     * @return Clear text
     */
    public static byte[] decryptDES(final byte[] data, final byte[] key) {
        return desTemplate(data, key, DES_Algorithm, DES_Transformation, false);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 3DES encryption related
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 3DES transformation
     * <p>Algorithm name / encryption mode / padding method</p>
     * <p>The encryption modes are: Electronic Code Mode ECB, Encryption Block Chain Mode CBC, Encryption Feedback Mode CFB, Output Feedback Mode OFB</p>
     * <p>There are stuffing methods：NoPadding、ZerosPadding、PKCS5Padding</p>
     */
    public static        String TripleDES_Transformation = "DESede/ECB/NoPadding";
    private static final String TripleDES_Algorithm      = "DESede";


    /**
     * 3DES is encrypted and converted to Base64 encoding
     *
     * @param data Clear text
     * @param key  24-byte key
     * @return Base64 ciphertext
     */
    public static byte[] encrypt3DES2Base64(final byte[] data, final byte[] key) {
        return base64Encode(encrypt3DES(data, key));
    }

    /**
     * 3DES is encrypted and converted to hexadecimal
     *
     * @param data  Clear text
     * @param key   24-byte key
     * @return 16 hexadecimal ciphertext
     */
    public static String encrypt3DES2HexString(final byte[] data, final byte[] key) {
        return bytes2HexString(encrypt3DES(data, key));
    }

    /**
     * 3DES is encrypted and converted to hexadecimal
     *
     * @param data Clear text
     * @param key  24-byte key
     * @return Ciphertext
     */
    public static byte[] encrypt3DES(final byte[] data, final byte[] key) {
        return desTemplate(data, key, TripleDES_Algorithm, TripleDES_Transformation, true);
    }

    /**
     * 3DES decrypts Base64 encoded ciphertext
     *
     * @param data Base64 encoded ciphertext
     * @param key   24-byte key
     * @return Ciphertext
     */
    public static byte[] decryptBase64_3DES(final byte[] data, final byte[] key) {
        return decrypt3DES(base64Decode(data), key);
    }

    /**
     * 3DES decrypts hexadecimal ciphertext
     *
     * @param data 16 hexadecimal ciphertext
     * @param key  24-byte key
     * @return Clear text
     */
    public static byte[] decryptHexString3DES(final String data, final byte[] key) {
        return decrypt3DES(hexString2Bytes(data), key);
    }

    /**
     * 3DES decryption
     *
     * @param data Ciphertext
     * @param key  24-byte key
     * @return Clear text
     */
    public static byte[] decrypt3DES(final byte[] data, final byte[] key) {
        return desTemplate(data, key, TripleDES_Algorithm, TripleDES_Transformation, false);
    }

    ///////////////////////////////////////////////////////////////////////////
    // ES encryption related
    ///////////////////////////////////////////////////////////////////////////

    /**
     * AES is encrypted and converted to Base64 encoding
     * <p>Algorithm name / encryption mode / padding method</p>
     * <p>The encryption modes are: Electronic Code Mode ECB, Encryption Block Chain Mode CBC, Encryption Feedback Mode CFB, Output Feedback Mode OFB</p>
     * <p>There are stuffing methods：NoPadding、ZerosPadding、PKCS5Padding</p>
     */
    public static        String AES_Transformation = "AES/ECB/NoPadding";
    private static final String AES_Algorithm      = "AES";


    /**
     * AES is encrypted and converted to Base64 encoding
     *
     * @param data Clear text
     * @param key  16,24,32 bytes of key
     * @return Base64 ciphertext
     */
    public static byte[] encryptAES2Base64(final byte[] data, final byte[] key) {
        return base64Encode(encryptAES(data, key));
    }

    /**
     * AES is encrypted and converted to hexadecimal
     *
     * @param data Clear text
     * @param key 16,24,32 bytes of key
     * @return 16 hexadecimal ciphertext
     */
    public static String encryptAES2HexString(final byte[] data, final byte[] key) {
        return bytes2HexString(encryptAES(data, key));
    }

    /**
     * AES encryption
     *
     * @param data Clear text
     * @param key  16,24,32 bytes of key
     * @return Ciphertext
     */
    public static byte[] encryptAES(final byte[] data, final byte[] key) {
        return desTemplate(data, key, AES_Algorithm, AES_Transformation, true);
    }

    /**
     * AES decrypts Base64 encoded ciphertext
     *
     * @param data Base64 encoded ciphertext
     * @param key  16,24,32 bytes of key
     * @return Clear text
     */
    public static byte[] decryptBase64AES(final byte[] data, final byte[] key) {
        return decryptAES(base64Decode(data), key);
    }

    /**
     * AES decrypted hexadecimal ciphertext
     *
     * @param data 16 hexadecimal ciphertext
     * @param key  16,24,32 bytes of key
     * @return Clear text
     */
    public static byte[] decryptHexStringAES(final String data, final byte[] key) {
        return decryptAES(hexString2Bytes(data), key);
    }

    /**
     * AES decryption
     *
     * @param data data
     * @param key  16,24,32 bytes of key
     * @return Clear text
     */
    public static byte[] decryptAES(final byte[] data, final byte[] key) {
        return desTemplate(data, key, AES_Algorithm, AES_Transformation, false);
    }

    /**
     * DES encryption template
     *
     * @param data           data
     * @param key            key
     * @param algorithm      Encryption Algorithm
     * @param transformation change
     * @param isEncrypt      {@code true}: encryption {@code false}: Decrypted
     * @return  Ciphertext or plaintext, for DES ，3DES，AES
     */
    public static byte[] desTemplate(final byte[] data, final byte[] key, final String algorithm, final String transformation, final boolean isEncrypt) {
        if (data == null || data.length == 0 || key == null || key.length == 0) return null;
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
            Cipher cipher = Cipher.getInstance(transformation);
            SecureRandom random = new SecureRandom();
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, random);
            return cipher.doFinal(data);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static String bytes2HexString(final byte[] bytes) {
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

    private static byte[] hexString2Bytes(String hexString) {
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

    private static int hex2Dec(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static byte[] base64Encode(final byte[] input) {
        return Base64.encode(input, Base64.NO_WRAP);
    }

    private static byte[] base64Decode(final byte[] input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }

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
