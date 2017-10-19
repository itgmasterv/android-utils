package com.example.sahmed.utilityapp.utility;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mehab on 10/18/2017.
 */

public class FileIOUtils {
    private FileIOUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static final String LINE_SEP = System.getProperty("line.separator");

    private static int sBufferSize = 8192;

    /**
     * Writes the input stream to a file
     *
     * @param filePath file Path
     * @param inputStream       input Stream
     * @return {@code true}: Success <br>{@code false}: fail
     */
    public static boolean writeFileFromInputStream(final String filePath, final InputStream inputStream) {
        return writeFileFromInputStream(getFileByPath(filePath), inputStream, false);
    }

    /**
     * Writes the input stream to a file
     *
     * @param filePath file Path
     * @param inputStream       input Stream
     * @param append   Whether it is added at the end of the document
     * @return {@code true}: Success write file <br>{@code false}:  Write fail
     */
    public static boolean writeFileFromInputStream(final String filePath, final InputStream inputStream, final boolean append) {
        return writeFileFromInputStream(getFileByPath(filePath), inputStream, append);
    }

    /**
     * Writes the input stream to a file
     *
     * @param file file
     * @param is   input Stream
     * @return {@code true}: Write success<br>{@code false}: Write fail
     */
    public static boolean writeFileFromInputStream(final File file, final InputStream is) {
        return writeFileFromInputStream(file, is, false);
    }

    /**
     * Writes the input stream to a file
     *
     * @param file   File
     * @param is     InputStream
     *
     * @param append Whether it is added at the end of the document
     * @return {@code true}: Write success<br>{@code false}: Write fail
     */
    public static boolean writeFileFromInputStream(final File file, final InputStream is, final boolean append) {
        if (!createOrExistsFile(file) || is == null) return false;
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append));
            byte data[] = new byte[sBufferSize];
            int len;
            while ((len = is.read(data, 0, sBufferSize)) != -1) {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseUtils.closeIO(is, os);
        }
    }

    /**
     * Write a byte array to a file
     *
     * @param filePath String
     * @param bytes    bytes arr
     * @return {@code true}: Write success<br>{@code false}: Write fail
     */
    public static boolean writeFileFromBytesByStream(final String filePath, final byte[] bytes) {
        return writeFileFromBytesByStream(getFileByPath(filePath), bytes, false);
    }

    /**
     * Write a byte array to a file
     *
     * @param filePath file Path
     * @param bytes     bytes arr
     * @param append   Whether it is added at the end of the document
     * @return {@code true}: Write success<br>{@code false}: Write fail
     */
    public static boolean writeFileFromBytesByStream(final String filePath, final byte[] bytes, final boolean append) {
        return writeFileFromBytesByStream(getFileByPath(filePath), bytes, append);
    }

    /**
     * Write a byte array to a file
     *
     * @param file  File
     * @param bytes bytes arr
     * @return {@code true}: Write success<br>{@code false}: Write fail
     */
    public static boolean writeFileFromBytesByStream(final File file, final byte[] bytes) {
        return writeFileFromBytesByStream(file, bytes, false);
    }

    /**
     * Write a byte array to a file
     *
     * @param file   File
     * @param bytes  bytes arr
     * @param append Whether it is added at the end of the document
     * @return {@code true}: Write success<br>{@code false}: Write fail
     */
    public static boolean writeFileFromBytesByStream(final File file, final byte[] bytes, final boolean append) {
        if (bytes == null || !createOrExistsFile(file)) return false;
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file, append));
            bos.write(bytes);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseUtils.closeIO(bos);
        }
    }

    /**
     * Write a byte array to a file
     *
     * @param filePath file Path
     * @param bytes    bytes arr
     * @param isForce  Whether to write a file
     * @return {@code true}: Write success<br>{@code false}: Write fail
     */
    public static boolean writeFileFromBytesByChannel(final String filePath, final byte[] bytes, final boolean isForce) {
        return writeFileFromBytesByChannel(getFileByPath(filePath), bytes, false, isForce);
    }

    /**
     * Write a byte array to a file
     *
     * @param filePath file Path
     * @param bytes    bytes arr
     * @param append   Whether it is added at the end of the document
     * @param isForce  Whether to write a file
     * @return {@code true}: Write success<br>{@code false}: Write fail
     */
    public static boolean writeFileFromBytesByChannel(final String filePath, final byte[] bytes, final boolean append, final boolean isForce) {
        return writeFileFromBytesByChannel(getFileByPath(filePath), bytes, append, isForce);
    }

    /**
     * Write a byte array to a file
     *
     * @param file    File
     * @param bytes   bytes array
     * @param isForce Whether to write a file
     * @return {@code true}: Write success<br>{@code false}: Write fail
     */
    public static boolean writeFileFromBytesByChannel(final File file, final byte[] bytes, final boolean isForce) {
        return writeFileFromBytesByChannel(file, bytes, false, isForce);
    }

    /**
     * Write a byte array to a file
     *
     * @param file    File
     * @param bytes   bytes array
     * @param append   Whether it is added at the end of the document
     * @param isForce Whether to write a file
     * @return {@code true}: Write success<br>{@code false}: Write fail
     */
    public static boolean writeFileFromBytesByChannel(final File file, final byte[] bytes, final boolean append, final boolean isForce) {
        if (bytes == null) return false;
        FileChannel fc = null;
        try {
            fc = new FileOutputStream(file, append).getChannel();
            fc.position(fc.size());
            fc.write(ByteBuffer.wrap(bytes));
            if (isForce) fc.force(true);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseUtils.closeIO(fc);
        }
    }

    /**
     * Write a byte array to a file
     *
     * @param filePath file Path
     * @param bytes    Byte array
     * @param isForce  Whether to write a file
     * @return {@code true}: Write success<br>{@code false}: Write fail
     */
    public static boolean writeFileFromBytesByMap(final String filePath, final byte[] bytes, final boolean isForce) {
        return writeFileFromBytesByMap(filePath, bytes, false, isForce);
    }

    /**
     * Write a byte array to a file
     *
     * @param filePath file Path
     * @param bytes     Byte array
     * @param append  Whether it is added at the end of the document
     * @param isForce  Whether to write a file
     * @return {@code true}: Write success<br>{@code false}: Write fail
     */
    public static boolean writeFileFromBytesByMap(final String filePath, final byte[] bytes, final boolean append, final boolean isForce) {
        return writeFileFromBytesByMap(getFileByPath(filePath), bytes, append, isForce);
    }

    /**
     * Write a byte array to a file
     *
     * @param file    File
     * @param bytes  Byte array
     * @param isForce Whether to write a file
     * @return {@code true}: Write success <br>{@code false}:Write fail
     */
    public static boolean writeFileFromBytesByMap(final File file, final byte[] bytes, final boolean isForce) {
        return writeFileFromBytesByMap(file, bytes, false, isForce);
    }

    /**
     * Write a byte array to a file
     *
     * @param file    File
     * @param bytes    Byte array
     * @param append Whether it is added at the end of the document
     * @param isForce Whether to write a file
     * @return {@code true}: Write success<br>{@code false}: Write fail
     */
    public static boolean writeFileFromBytesByMap(final File file, final byte[] bytes, final boolean append, final boolean isForce) {
        if (bytes == null || !createOrExistsFile(file)) return false;
        FileChannel fc = null;
        try {
            fc = new FileOutputStream(file, append).getChannel();
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, fc.size(), bytes.length);
            mbb.put(bytes);
            if (isForce) mbb.force();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseUtils.closeIO(fc);
        }
    }

    /**
     * Write a string to a file
     *
     * @param filePath file Path
     * @param content  Write content
     * @return {@code true}: Write success <br>{@code false}: Write fail
     */
    public static boolean writeFileFromString(final String filePath, final String content) {
        return writeFileFromString(getFileByPath(filePath), content, false);
    }

    /**
     * Write a string to a file
     *
     * @param filePath file Path
     * @param content  Write content
     * @param append   Whether it is added at the end of the document
     * @return {@code true}: Write success<br>{@code false}: 写入失败
     */
    public static boolean writeFileFromString(final String filePath, final String content, final boolean append) {
        return writeFileFromString(getFileByPath(filePath), content, append);
    }

    /**
     * Write a string to a file
     *
     * @param file    File
     * @param content  Write content
     * @return {@code true}: Write success<br>{@code false}: Write fail
     */
    public static boolean writeFileFromString(final File file, final String content) {
        return writeFileFromString(file, content, false);
    }

    /**
     * Write a string to a file
     *
     * @param file    File
     * @param content  Write content
     * @param append   Whether it is added at the end of the document
     * @return {@code true}: Write success <br>{@code false}: Write fail
     */
    public static boolean writeFileFromString(final File file, final String content, final boolean append) {
        if (file == null || content == null) return false;
        if (!createOrExistsFile(file)) return false;
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, append));
            bw.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseUtils.closeIO(bw);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // the divide line of write and read
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Read the file into the string list
     *
     * @param filePath file Path
     * @return String in the list
     */
    public static List<String> readFile2List(final String filePath) {
        return readFile2List(getFileByPath(filePath), null);
    }

    /**
     * Read the file into the string list
     *
     * @param filePath    file Path
     * @param charsetName charset Name
     * @return String in the list
     */
    public static List<String> readFile2List(final String filePath, final String charsetName) {
        return readFile2List(getFileByPath(filePath), charsetName);
    }

    /**
     * Read the file into the string list
     *
     * @param file File
     * @return String in the list
     */
    public static List<String> readFile2List(final File file) {
        return readFile2List(file, 0, 0x7FFFFFFF, null);
    }

    /**
     * Read the file into the string list
     *
     * @param file        File
     * @param charsetName charset Name
     * @return String in the list
     */
    public static List<String> readFile2List(final File file, final String charsetName) {
        return readFile2List(file, 0, 0x7FFFFFFF, charsetName);
    }

    /**
     * Read the file into the string list
     *
     * @param filePath file Path
     * @param startsToRead       Number of starts to read
     * @param end        The number of end lines to read
     * @return String in the list
     */
    public static List<String> readFile2List(final String filePath, final int startsToRead, final int end) {
        return readFile2List(getFileByPath(filePath), startsToRead, end, null);
    }

    /**
     * Read the file into the string list
     *
     * @param filePath    file Path
     * @param startsToRead          Number of starts to read
     * @param end         The number of end lines to read
     * @param charsetName charset Name
     * @return String in the list
     */
    public static List<String> readFile2List(final String filePath, final int startsToRead, final int end, final String charsetName) {
        return readFile2List(getFileByPath(filePath), startsToRead, end, charsetName);
    }

    /**
     * Read the file into the string list
     *
     * @param file File
     * @param startsToRead    Number of starts to read
     * @param end  The number of end lines to read
     * @return String in the list
     */
    public static List<String> readFile2List(final File file, final int startsToRead, final int end) {
        return readFile2List(file, startsToRead, end, null);
    }

    /**
     * Read the file into the string list
     *
     * @param file        File
     * @param startsToRead           Number of starts to read
     * @param end         The number of end lines to read
     * @param charsetName charset Name
     * @return String in the list
     */
    public static List<String> readFile2List(final File file, final int startsToRead, final int end, final String charsetName) {
        if (!isFileExists(file)) return null;
        if (startsToRead > end) return null;
        BufferedReader reader = null;
        try {
            String line;
            int curLine = 1;
            List<String> list = new ArrayList<>();
            if (isSpace(charsetName)) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } else {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            }
            while ((line = reader.readLine()) != null) {
                if (curLine > end) break;
                if (startsToRead <= curLine && curLine <= end) list.add(line);
                ++curLine;
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(reader);
        }
    }

    /**
     * Read the file into the string
     *
     * @param filePath file Path
     * @return String
     */
    public static String readFile2String(final String filePath) {
        return readFile2String(getFileByPath(filePath), null);
    }

    /**
     * Read the file into the string
     *
     * @param filePath    file Path
     * @param charsetName charset Name
     * @return String
     */
    public static String readFile2String(final String filePath, final String charsetName) {
        return readFile2String(getFileByPath(filePath), charsetName);
    }

    /**
     * Read the file into the string
     *
     * @param file File
     * @return String
     */
    public static String readFile2String(final File file) {
        return readFile2String(file, null);
    }

    /**
     * Read the file into the string
     *
     * @param file        File
     * @param charsetName charset Name
     * @return String
     */
    public static String readFile2String(final File file, final String charsetName) {
        if (!isFileExists(file)) return null;
        BufferedReader reader = null;
        try {
            StringBuilder sb = new StringBuilder();
            if (isSpace(charsetName)) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } else {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            }
            String line;
            if ((line = reader.readLine()) != null) {
                sb.append(line);
                while ((line = reader.readLine()) != null) {
                    sb.append(LINE_SEP).append(line);
                }
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(reader);
        }
    }

    /**
     * Read the file into the byte array
     *
     * @param filePath file Path
     * @return 字符数组
     */
    public static byte[] readFile2BytesByStream(final String filePath) {
        return readFile2BytesByStream(getFileByPath(filePath));
    }

    /**
     * Read the file into the byte array
     *
     * @param file File
     * @return Character array
     */
    public static byte[] readFile2BytesByStream(final File file) {
        if (!isFileExists(file)) return null;
        FileInputStream fis = null;
        ByteArrayOutputStream os = null;
        try {
            fis = new FileInputStream(file);
            os = new ByteArrayOutputStream();
            byte[] b = new byte[sBufferSize];
            int len;
            while ((len = fis.read(b, 0, sBufferSize)) != -1) {
                os.write(b, 0, len);
            }
            return os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(fis, os);
        }
    }

    /**
     * Read the file into the byte array
     *
     * @param filePath file Path
     * @return Character array
     */
    public static byte[] readFile2BytesByChannel(final String filePath) {
        return readFile2BytesByChannel(getFileByPath(filePath));
    }

    /**
     * Read the file into the byte array
     *
     * @param file File
     * @return Character array
     */
    public static byte[] readFile2BytesByChannel(final File file) {
        if (!isFileExists(file)) return null;
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(file, "r").getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) fc.size());
            while (true) {
                if (!((fc.read(byteBuffer)) > 0)) break;
            }
            return byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(fc);
        }
    }

    /**
     * Read the file into the byte array
     *
     * @param filePath file Path
     * @return Character array
     */
    public static byte[] readFile2BytesByMap(final String filePath) {
        return readFile2BytesByMap(getFileByPath(filePath));
    }

    /**
     * Read the file into the byte array
     *
     * @param file file
     * @return Character array
     */
    public static byte[] readFile2BytesByMap(final File file) {
        if (!isFileExists(file)) return null;
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(file, "r").getChannel();
            int size = (int) fc.size();
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, size).load();
            byte[] result = new byte[size];
            mbb.get(result, 0, size);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(fc);
        }
    }

    /**
     * Set the buffer size
     *
     * @param bufferSize Buffer size
     */
    public static void setBufferSize(final int bufferSize) {
        sBufferSize = bufferSize;
    }

    private static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private static boolean createOrExistsFile(final String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    private static boolean createOrExistsFile(final File file) {
        if (file == null) return false;
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private static boolean isFileExists(final File file) {
        return file != null && file.exists();
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
