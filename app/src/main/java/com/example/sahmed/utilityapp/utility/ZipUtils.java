package com.example.sahmed.utilityapp.utility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by mehab on 10/19/2017.
 */

public class ZipUtils {

    private static final int BUFFER_LEN = 8192;

    private ZipUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Compressed file
     *
     * @param resFilePath The file path to be compressed
     * @param zipFilePath Compress the file path
     * @return {@code true}: Compression is successful<br>{@code false}: Compression failed
     * @throws IOException IO error when thrown
     */
    public static boolean zipFile(final String resFilePath,
                                  final String zipFilePath)
            throws IOException {
        return zipFile(resFilePath, zipFilePath, null);
    }

    /**
     * Compressed file

     *
     * @param resFilePath The file path to be compressed
     * @param zipFilePath The file path to be compressed
     * @param comment     Comment on compressed files
     * @return {@code true}: Compression is successful<br>{@code false}: Compression failed
     * @throws IOException IO error when thrown
     */
    public static boolean zipFile(final String resFilePath,
                                  final String zipFilePath,
                                  final String comment)
            throws IOException {
        return zipFile(getFileByPath(resFilePath), getFileByPath(zipFilePath), comment);
    }

    /**
     * Compressed file
     *
     * @param resFile To be compressed
     * @param zipFile Compressed file
     * @return {@code true}: Compression is successful<br>{@code false}: Compression failed
     * @throws IOException IO error when thrown
     */
    public static boolean zipFile(final File resFile,
                                  final File zipFile)
            throws IOException {
        return zipFile(resFile, zipFile, null);
    }

    /**
     * Compressed file
     *
     * @param resFile To be compressed
     * @param zipFile Compressed file
     * @param comment Comment on compressed files
     * @return {@code true}: Compression is successful<br>{@code false}: Compression failed
     * @throws IOException IO error when thrown
     */
    public static boolean zipFile(final File resFile,
                                  final File zipFile,
                                  final String comment)
            throws IOException {
        if (resFile == null || zipFile == null) return false;
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFile));
            return zipFile(resFile, "", zos, comment);
        } finally {
            if (zos != null) {
                CloseUtils.closeIO(zos);
            }
        }
    }

    /**
     * Compressed file
     *
     * @param resFile  To be compressed
     * @param rootPath Relative to the compressed file path
     * @param zos      Compress the file output stream
     * @param comment  Comment on compressed files
     * @return {@code true}: Compression Success <br>{@code false}: Compression failed
     * @throws IOException IO error when thrown
     */
    private static boolean zipFile(final File resFile,
                                   String rootPath,
                                   final ZipOutputStream zos,
                                   final String comment)
            throws IOException {
        rootPath = rootPath + (isSpace(rootPath) ? "" : File.separator) + resFile.getName();
        if (resFile.isDirectory()) {
            File[] fileList = resFile.listFiles();
            // If it is empty folder to create it, I put '/' for File.separator test is not successful, eggPain
            if (fileList == null || fileList.length <= 0) {
                ZipEntry entry = new ZipEntry(rootPath + '/');
                if (!isSpace(comment)) entry.setComment(comment);
                zos.putNextEntry(entry);
                zos.closeEntry();
            } else {
                for (File file : fileList) {
                    // Returns false if recursive returns false
                    if (!zipFile(file, rootPath, zos, comment)) return false;
                }
            }
        } else {
            InputStream is = null;
            try {
                is = new BufferedInputStream(new FileInputStream(resFile));
                ZipEntry entry = new ZipEntry(rootPath);
                if (!isSpace(comment)) entry.setComment(comment);
                zos.putNextEntry(entry);
                byte buffer[] = new byte[BUFFER_LEN];
                int len;
                while ((len = is.read(buffer, 0, BUFFER_LEN)) != -1) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
            } finally {
                CloseUtils.closeIO(is);
            }
        }
        return true;
    }

    /**
     * unzip File
     *
     * @param zipFilePath The file path to be unpacked
     * @param destDirPath Target directory path
     * @return File list
     * @throws IOException IO error when thrown
     */
    public static List<File> unzipFile(final String zipFilePath,
                                       final String destDirPath)
            throws IOException {
        return unzipFileByKeyword(zipFilePath, destDirPath, null);
    }

    /**
     * unzip File
     *
     * @param zipFile zip File
     * @param destDir Target directory
     * @return File list
     * @throws IOException IO error when thrown
     */
    public static List<File> unzipFile(final File zipFile,
                                       final File destDir)
            throws IOException {
        return unzipFileByKeyword(zipFile, destDir, null);
    }

    /**
     * Unzip the file with the keyword
     *
     * @param zipFilePath The file path to be unpacked
     * @param destDirPath Target directory path
     * @param keyword     keywords
     * @return Returns a list of files with keywords
     * @throws IOException IO error when thrown
     */
    public static List<File> unzipFileByKeyword(final String zipFilePath,
                                                final String destDirPath,
                                                final String keyword)
            throws IOException {
        return unzipFileByKeyword(getFileByPath(zipFilePath), getFileByPath(destDirPath), keyword);
    }

    /**
     * Unzip the file with the keyword
     *
     * @param zipFile File to be decompressed
     * @param destDir Target directory
     * @param keyword keywords
     * @return Returns a list of files with keywords
     * @throws IOException IO error when thrown
     */
    public static List<File> unzipFileByKeyword(final File zipFile,
                                                final File destDir,
                                                final String keyword)
            throws IOException {
        if (zipFile == null || destDir == null) return null;
        List<File> files = new ArrayList<>();
        ZipFile zf = new ZipFile(zipFile);
        Enumeration<?> entries = zf.entries();
        if (isSpace(keyword)) {
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (!unzipChildFile(destDir, files, zf, entry, entryName)) return files;
            }
        } else {
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.contains(keyword)) {
                    if (!unzipChildFile(destDir, files, zf, entry, entryName)) return files;
                }
            }
        }
        return files;
    }

    private static boolean unzipChildFile(File destDir, List<File> files, ZipFile zf, ZipEntry entry, String entryName) throws IOException {
        String filePath = destDir + File.separator + entryName;
        File file = new File(filePath);
        files.add(file);
        if (entry.isDirectory()) {
            if (!createOrExistsDir(file)) return false;
        } else {
            if (!createOrExistsFile(file)) return false;
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new BufferedInputStream(zf.getInputStream(entry));
                out = new BufferedOutputStream(new FileOutputStream(file));
                byte buffer[] = new byte[BUFFER_LEN];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            } finally {
                CloseUtils.closeIO(in, out);
            }
        }
        return true;
    }

    /**
     * Gets the file path list in the compressed file
     *
     * @param zipFilePath Compress the file path
     * @return The file path list in the compressed file
     * @throws IOException IO error when thrown
     */
    public static List<String> getFilesPath(final String zipFilePath)
            throws IOException {
        return getFilesPath(getFileByPath(zipFilePath));
    }

    /**
     * Gets the file path list in the compressed file
     *
     * @param zipFile Compressed file

     * @return The file path list in the compressed file
     * @throws IOException IO error when thrown
     */
    public static List<String> getFilesPath(final File zipFile)
            throws IOException {
        if (zipFile == null) return null;
        List<String> paths = new ArrayList<>();
        Enumeration<?> entries = new ZipFile(zipFile).entries();
        while (entries.hasMoreElements()) {
            paths.add(((ZipEntry) entries.nextElement()).getName());
        }
        return paths;
    }

    /**
     * Gets the comment list in the zip file
     *
     * @param zipFilePath Compress the file path
     * @return Compress the list of comments in the file
     * @throws IOException IO error when thrown
     */
    public static List<String> getComments(final String zipFilePath)
            throws IOException {
        return getComments(getFileByPath(zipFilePath));
    }

    /**
     * Gets the comment list in the zip file
     *
     * @param zipFile Compressed file
     * @return Compress the list of comments in the file
     * @throws IOException IO error when thrown
     */
    public static List<String> getComments(final File zipFile)
            throws IOException {
        if (zipFile == null) return null;
        List<String> comments = new ArrayList<>();
        Enumeration<?> entries = new ZipFile(zipFile).entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = ((ZipEntry) entries.nextElement());
            comments.add(entry.getComment());
        }
        return comments;
    }

    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
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

    private static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
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
