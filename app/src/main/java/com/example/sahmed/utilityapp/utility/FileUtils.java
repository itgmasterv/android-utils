package com.example.sahmed.utilityapp.utility;

import android.annotation.SuppressLint;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mehab on 10/18/2017.
 */

public class FileUtils {
    private FileUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static final String LINE_SEP = System.getProperty("line.separator");

    /**
     * Obtain the file according to the file path
     *
     * @param filePath file Path
     * @return file
     */
    public static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    /**
     * To determine whether the file exists
     *
     * @param filePath file Path
     * @return {@code true}: exist<br>{@code false}: does not exist
     */
    public static boolean isFileExists(final String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    /**
     * To determine whether the file exists
     *
     * @param file file
     * @return {@code true}: exist<br>{@code false}: does not exist
     */
    public static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    /**
     * Rename the file
     *
     * @param filePath file Path
     * @param newName  New name
     * @return {@code true}: Rename successfully<br>{@code false}: Rename failed
     */
    public static boolean rename(final String filePath, final String newName) {
        return rename(getFileByPath(filePath), newName);
    }

    /**
     * Rename the file
     *
     * @param file    file
     * @param newName New name
     * @return {@code true}: Rename successfully<br>{@code false}: Rename failed
     */
    public static boolean rename(final File file, final String newName) {
        // The file is empty and returns false
        if (file == null) return false;
        // The file does not exist to return false
        if (!file.exists()) return false;
        // The new file name is empty to return false
        if (isSpace(newName)) return false;
        //Returns true if the file name does not change
        if (newName.equals(file.getName())) return true;
        File newFile = new File(file.getParent() + File.separator + newName);
        // If the renamed file already exists, returns false
        return !newFile.exists()
                && file.renameTo(newFile);
    }

    /**
     * To determine whether the directory
     *
     * @param dirPath Directory path
     * @return {@code true}: yes <br>{@code false}: no
     */
    public static boolean isDir(final String dirPath) {
        return isDir(getFileByPath(dirPath));
    }

    /**
     * To determine whether the directory
     *
     * @param file file
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isDir(final File file) {
        return file != null && file.exists() && file.isDirectory();
    }

    /**
     * To determine whether it is a document
     *
     * @param filePath file Path
     * @return {@code true}: yes <br>{@code false}: no
     */
    public static boolean isFile(final String filePath) {
        return isFile(getFileByPath(filePath));
    }

    /**
     * To determine whether it is a document
     *
     * @param file file
     * @return {@code true}: yes <br>{@code false}: no
     */
    public static boolean isFile(final File file) {
        return file != null && file.exists() && file.isFile();
    }

    /**
     * To determine whether the existence of the directory, there is no judgment to determine whether to create a success
     *
     * @param dirPath Directory path
     * @return {@code true}: Exist or create success<br>{@code false}: Does not exist or fails to create
     */
    public static boolean createOrExistsDir(final String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    /**
     * To determine whether the existence of the directory, there is no judgment to determine whether to create a success
     *
     * @param file file
     * @return {@code true}: Exist or create success<br>{@code false}: Does not exist or fails to create
     */
    public static boolean createOrExistsDir(final File file) {
        // If it is, is the directory is true, the file is returned false, there is no return to whether to create a successful
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * To determine whether the existence of the file, there is no judgment to determine whether to create a success
     *
     * @param filePath file Path
     * @return {@code true}: Exist or create success<br>{@code false}: Does not exist or fails to create
     */
    public static boolean createOrExistsFile(final String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    /**
     * To determine whether the existence of the file, there is no judgment to determine whether to create a success
     *
     * @param file file
     * @return {@code true}: Exist or create success <br>{@code false}: Does not exist or fails to create
     */
    public static boolean createOrExistsFile(final File file) {
        if (file == null) return false;
        // If it is a file, it returns true, and the directory returns false
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * To determine whether the file exists, the existence of the deletion before the creation
     *
     * @param filePath file Path
     * @return {@code true}: Create success<br>{@code false}: Failed to create
     */
    public static boolean createFileByDeleteOldFile(final String filePath) {
        return createFileByDeleteOldFile(getFileByPath(filePath));
    }

    /**
     * To determine whether the file exists, the existence of the deletion before the creation
     *
     * @param file file
     * @return {@code true}: Create success<br>{@code false}: Failed to create
     */
    public static boolean createFileByDeleteOldFile(final File file) {
        if (file == null) return false;
        // The file exists and the deletion fails to return false
        if (file.exists() && !file.delete()) return false;
        // Failed to create directory failed to return false
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Copy or move the directory
     *
     * @param srcDirPath  Source directory path
     * @param destDirPath Target directory path
     * @param listener    Whether to cover the listener
     * @param isMove      Whether to move
     * @return {@code true}: Copy or move successfully<br>{@code false}: Copy or move failed
     */
    private static boolean copyOrMoveDir(final String srcDirPath, final String destDirPath, final OnReplaceListener listener, final boolean isMove) {
        return copyOrMoveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), listener, isMove);
    }

    /**
     * Copy or move the directory
     *
     * @param srcDir   Source directory
     * @param destDir  Target directory
     * @param listener Whether to cover the listener
     * @param isMove   Whether to move
     * @return {@code true}: Copy or move successfully<br>{@code false}: Copy or move failed
     */
    private static boolean copyOrMoveDir(final File srcDir, final File destDir, final OnReplaceListener listener, final boolean isMove) {
        if (srcDir == null || destDir == null) return false;
        // If the target directory in the source directory is returned to false, do not understand if you think about how recursive
        // srcPath : F:\\MyGithub\\AndroidUtilCode\\utilcode\\src\\test\\res
        // destPath: F:\\MyGithub\\AndroidUtilCode\\utilcode\\src\\test\\res1
        // In order to prevent the above situation there is a miscarriage of justice, respectively, in the back to add a path separator
        String srcPath = srcDir.getPath() + File.separator;
        String destPath = destDir.getPath() + File.separator;
        if (destPath.contains(srcPath)) return false;
        // The source file does not exist or is not a directory that returns false
        if (!srcDir.exists() || !srcDir.isDirectory()) return false;
        if (destDir.exists()) {
            if (listener.onReplace()) {// Need to overwrite the old directory
                if (!deleteAllInDir(destDir)) {// If the file fails, it returns false
                    return false;
                }
            } else {// Do not need to overwrite the direct return can be true
                return true;
            }
        }
        // The destination directory does not exist to return false
        if (!createOrExistsDir(destDir)) return false;
        File[] files = srcDir.listFiles();
        for (File file : files) {
            File oneDestFile = new File(destPath + file.getName());
            if (file.isFile()) {
                // If the operation fails, it returns false
                if (!copyOrMoveFile(file, oneDestFile, listener, isMove)) return false;
            } else if (file.isDirectory()) {
                // If the operation fails, it returns false
                if (!copyOrMoveDir(file, oneDestFile, listener, isMove)) return false;
            }
        }
        return !isMove || deleteDir(srcDir);
    }

    /**
     * Copy or move files
     *
     * @param srcFilePath  Source file path
     * @param destFilePath Target file path
     * @param listener     Whether to cover the listener
     * @param isMove       Whether to move
     * @return {@code true}: Copy or move successfully<br>{@code false}: Copy or move failed
     */
    private static boolean copyOrMoveFile(final String srcFilePath, final String destFilePath, final OnReplaceListener listener, final boolean isMove) {
        return copyOrMoveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), listener, isMove);
    }

    /**
     * Copy or move files
     *
     * @param srcFile  Source File
     * @param destFile Target file
     * @param listener Whether to cover the listener
     * @param isMove  Whether to move
     * @return {@code true}: Copy or move successfully<br>{@code false}: Copy or move failed
     */
    private static boolean copyOrMoveFile(final File srcFile, final File destFile, final OnReplaceListener listener, final boolean isMove) {
        if (srcFile == null || destFile == null) return false;
        // Returns false if the source and destination files are the same
        if (srcFile.equals(destFile)) return false;
         // The source file does not exist or is not a file that returns false
        if (!srcFile.exists() || !srcFile.isFile()) return false;
        if (destFile.exists()) {// The target file exists
            if (listener.onReplace()) {// Need to overwrite the old file
                if (!destFile.delete()) {// If the file fails, it returns false
                    return false;
                }
            } else {// Do not need to overwrite the direct return can be true
                return true;
            }
        }
        // The destination directory does not exist to return false
        if (!createOrExistsDir(destFile.getParentFile())) return false;
        try {
            return FileIOUtils.writeFileFromInputStream(destFile, new FileInputStream(srcFile), false)
                    && !(isMove && !deleteFile(srcFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Copy the directory
     *
     * @param srcDirPath  Source directory path
     * @param destDirPath Target directory path
     * @param listener    Whether to cover the listener
     * @return {@code true}: Copy successful<br>{@code false}: Copy failed
     */
    public static boolean copyDir(final String srcDirPath, final String destDirPath, final OnReplaceListener listener) {
        return copyDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), listener);
    }

    /**
     * Copy the directory
     *
     * @param srcDir   Source directory
     * @param destDir  Target directory
     * @param listener Whether to cover the listener
     * @return {@code true}: Copy successful<br>{@code false}: Copy failed
     */
    public static boolean copyDir(final File srcDir, final File destDir, final OnReplaceListener listener) {
        return copyOrMoveDir(srcDir, destDir, listener, false);
    }

    /**
     * Copy the file
     *
     * @param srcFilePath  Source file path
     * @param destFilePath Target file path
     * @param listener     Whether to cover the listener
     * @return {@code true}: Copy successful<br>{@code false}: Copy failed
     */
    public static boolean copyFile(final String srcFilePath, final String destFilePath, final OnReplaceListener listener) {
        return copyFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), listener);
    }

    /**
     * Copy the file
     *
     * @param srcFile  Source File
     * @param destFile Target file
     * @param listener Whether to cover the listener
     * @return {@code true}: Copy successful<br>{@code false}: Copy failed
     */
    public static boolean copyFile(final File srcFile, final File destFile, final OnReplaceListener listener) {
        return copyOrMoveFile(srcFile, destFile, listener, false);
    }

    /**
     * Move the directory
     *
     * @param srcDirPath  Source directory path
     * @param destDirPath Target directory path
     * @param listener    Whether to cover the listener
     * @return {@code true}: Move successfully<br>{@code false}: Move failed
     */
    public static boolean moveDir(final String srcDirPath, final String destDirPath, final OnReplaceListener listener) {
        return moveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), listener);
    }

    /**
     * Move the directory
     *
     * @param srcDir   Source directory
     * @param destDir  Target directory
     * @param listener Whether to cover the listener
     * @return {@code true}: Move successfully<br>{@code false}: Move failed
     */
    public static boolean moveDir(final File srcDir, final File destDir, final OnReplaceListener listener) {
        return copyOrMoveDir(srcDir, destDir, listener, true);
    }

    /**
     * Move the file
     *
     * @param srcFilePath  Source file path
     * @param destFilePath Target file path
     * @param listener     Whether to cover the listener
     * @return {@code true}: Move successfully<br>{@code false}: Move failed
     */
    public static boolean moveFile(final String srcFilePath, final String destFilePath, final OnReplaceListener listener) {
        return moveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), listener);
    }

    /**
     * Move the file
     *
     * @param srcFile  Source File
     * @param destFile Target file
     * @param listener Whether to cover the listener
     * @return {@code true}: Move successfully<br>{@code false}: Move failed
     */
    public static boolean moveFile(final File srcFile, final File destFile, final OnReplaceListener listener) {
        return copyOrMoveFile(srcFile, destFile, listener, true);
    }

    /**
     * Delete the directory
     *
     * @param dirPath Directory path
     * @return {@code true}: successfully deleted<br>{@code false}: failed to delete
     */
    public static boolean deleteDir(final String dirPath) {
        return deleteDir(getFileByPath(dirPath));
    }

    /**
     * Delete the directory
     *
     * @param dir table of Contents
     * @return {@code true}: successfully deleted<br>{@code false}: failed to delete
     */
    public static boolean deleteDir(final File dir) {
        if (dir == null) return false;
        // The directory does not exist returns true
        if (!dir.exists()) return true;
        // Not the directory returns false
        if (!dir.isDirectory()) return false;
        // Now the file exists and is the folder
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * Delete Files

     *
     * @param srcFilePath file path
     * @return {@code true}: successfully deleted
    <br>{@code false}: failed to delete
     */
    public static boolean deleteFile(final String srcFilePath) {
        return deleteFile(getFileByPath(srcFilePath));
    }

    /**
     * Delete Files
     *
     * @param file file
     * @return {@code true}: successfully deleted<br>{@code false}: failed to delete
     */
    public static boolean deleteFile(final File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }

    /**
     * Delete everything under the directory
     *
     * @param dirPath Directory path
     * @return {@code true}: successfully deleted
    <br>{@code false}: failed to delete
     */
    public static boolean deleteAllInDir(final String dirPath) {
        return deleteAllInDir(getFileByPath(dirPath));
    }

    /**
     * Delete everything under the directory
     *
     * @param dir table of Contents
     * @return {@code true}: successfully deleted<br>{@code false}: failed to delete
     */
    public static boolean deleteAllInDir(final File dir) {
        return deleteFilesInDirWithFilter(dir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        });
    }

    /**
     * Delete all files in the directory
     *
     * @param dirPath Directory path
     * @return {@code true}: successfully deleted
    <br>{@code false}: failed to delete
     */
    public static boolean deleteFilesInDir(final String dirPath) {
        return deleteFilesInDir(getFileByPath(dirPath));
    }

    /**
     * Delete all files in the directory
     *
     * @param dir table of Contents
     * @return {@code true}: successfully deleted<br>{@code false}: failed to delete
     */
    public static boolean deleteFilesInDir(final File dir) {
        return deleteFilesInDirWithFilter(dir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });
    }

    /**
     * Delete all filtered files in the directory
     *
     * @param dirPath Directory path
     * @param filter  filter
     * @return {@code true}: successfully deleted<br>{@code false}: failed to delete
     */
    public static boolean deleteFilesInDirWithFilter(final String dirPath, final FileFilter filter) {
        return deleteFilesInDirWithFilter(getFileByPath(dirPath), filter);
    }

    /**
     * Delete all filtered files in the directory
     *
     * @param dir    table of Contents
     * @param filter filter
     * @return {@code true}: successfully deleted<br>{@code false}: failed to delete
     */
    public static boolean deleteFilesInDirWithFilter(final File dir, final FileFilter filter) {
        if (dir == null) return false;
        // The directory does not exist returns true
        if (!dir.exists()) return true;
        // Not the directory returns false
        if (!dir.isDirectory()) return false;
        // Now the file exists and is the folder
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (filter.accept(file)) {
                    if (file.isFile()) {
                        if (!file.delete()) return false;
                    } else if (file.isDirectory()) {
                        if (!deleteDir(file)) return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Get all the files in the directory
     * <p>Not recursive subdirectories</p>
     *
     * @param dirPath Directory path
     * @return File list
     */
    public static List<File> listFilesInDir(final String dirPath) {
        return listFilesInDir(dirPath, false);
    }

    /**
     * Get all the files in the directory
     * <p>Not recursive subdirectories</p>
     *
     * @param dir table of Contents
     * @return File list
     */
    public static List<File> listFilesInDir(final File dir) {
        return listFilesInDir(dir, false);
    }

    /**
     * Get all the files in the directory
     *
     * @param dirPath     Directory path
     * @param isRecursive Whether recursive into the subdirectory
     * @return File list
     */
    public static List<File> listFilesInDir(final String dirPath, final boolean isRecursive) {
        return listFilesInDir(getFileByPath(dirPath), isRecursive);
    }

    /**
     * Get all the files in the directory
     *
     * @param dir         table of Contents
     * @param isRecursive Whether recursive into the subdirectory
     * @return File list
     */
    public static List<File> listFilesInDir(final File dir, final boolean isRecursive) {
        return listFilesInDirWithFilter(dir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        }, isRecursive);
    }

    /**
     * Get all filtered files in the directory
     * <p>Not recursive subdirectories</p>
     *
     * @param dirPath Directory path
     * @param filter  filter
     * @return File list
     */
    public static List<File> listFilesInDirWithFilter(final String dirPath,
                                                      final FileFilter filter) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), filter, false);
    }

    /**
     * Get all filtered files in the directory
     * <p>Not recursive subdirectories</p>
     *
     * @param dir    table of Contents
     * @param filter filter

     * @return File list
     */
    public static List<File> listFilesInDirWithFilter(final File dir,
                                                      final FileFilter filter) {
        return listFilesInDirWithFilter(dir, filter, false);
    }

    /**
     * Get all filtered files in the directory
     *
     * @param dirPath     Directory path
     * @param filter      filter
     * @param isRecursive Whether recursive into the subdirectory
     * @return File list
     */
    public static List<File> listFilesInDirWithFilter(final String dirPath,
                                                      final FileFilter filter,
                                                      final boolean isRecursive) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), filter, isRecursive);
    }

    /**
     * Get all filtered files in the directory
     *
     * @param dir         table of Contents
     * @param filter      filter
     * @param isRecursive Whether recursive into the subdirectory
     * @return File list
     */
    public static List<File> listFilesInDirWithFilter(final File dir,
                                                      final FileFilter filter,
                                                      final boolean isRecursive) {
        if (!isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (filter.accept(file)) {
                    list.add(file);
                }
                if (isRecursive && file.isDirectory()) {
                    //noinspection ConstantConditions
                    list.addAll(listFilesInDirWithFilter(file, filter, true));
                }
            }
        }
        return list;
    }

    /**
     * Get the last modified timestamp of the file
     *
     * @param filePath file path
     * @return The file is last modified by the millisecond timestamp
     */

    public static long getFileLastModified(final String filePath) {
        return getFileLastModified(getFileByPath(filePath));
    }

    /**
     * Get the last modified timestamp of the file
     *
     * @param file file
     * @return The file is last modified by the millisecond timestamp
     */
    public static long getFileLastModified(final File file) {
        if (file == null) return -1;
        return file.lastModified();
    }

    /**
     * Simply get the file encoding format
     *
     * @param filePath file Path
     * @return file encoding
     */
    public static String getFileCharsetSimple(final String filePath) {
        return getFileCharsetSimple(getFileByPath(filePath));
    }

    /**
     * Simply get the file encoding format
     *
     * @param file file
     * @return file encoding
     */
    public static String getFileCharsetSimple(final File file) {
        int p = 0;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            p = (is.read() << 8) + is.read();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(is);
        }
        switch (p) {
            case 0xefbb:
                return "UTF-8";
            case 0xfffe:
                return "Unicode";
            case 0xfeff:
                return "UTF-16BE";
            default:
                return "GBK";
        }
    }

    /**
     * Get the number of rows
     *
     * @param filePath file Path
     * @return Number of file lines
     */
    public static int getFileLines(final String filePath) {
        return getFileLines(getFileByPath(filePath));
    }

    /**
     * Get the number of rows
     * <p>Much faster than readLine</p>
     *
     * @param file file
     * @return Number of file lines
     */
    public static int getFileLines(final File file) {
        int count = 1;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024];
            int readChars;
            if (LINE_SEP.endsWith("\n")) {
                while ((readChars = is.read(buffer, 0, 1024)) != -1) {
                    for (int i = 0; i < readChars; ++i) {
                        if (buffer[i] == '\n') ++count;
                    }
                }
            } else {
                while ((readChars = is.read(buffer, 0, 1024)) != -1) {
                    for (int i = 0; i < readChars; ++i) {
                        if (buffer[i] == '\r') ++count;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(is);
        }
        return count;
    }

    /**
     * Get the directory size
     *
     * @param dirPath Directory path
     * @return File size
     */
    public static String getDirSize(final String dirPath) {
        return getDirSize(getFileByPath(dirPath));
    }

    /**
     * Get the directory size
     *
     * @param dir table of Contents
     * @return File size
     */
    public static String getDirSize(final File dir) {
        long len = getDirLength(dir);
        return len == -1 ? "" : byte2FitMemorySize(len);
    }

    /**
     * Get the file size
     *
     * @param filePath file Path
     * @return File size
     */
    public static String getFileSize(final String filePath) {
        return getFileSize(getFileByPath(filePath));
    }

    /**
     * Get the file size
     *
     * @param file file
     * @return File size
     */
    public static String getFileSize(final File file) {
        long len = getFileLength(file);
        return len == -1 ? "" : byte2FitMemorySize(len);
    }

    /**
     * Get the directory length
     *
     * @param dirPath Directory path
     * @return Directory length
     */
    public static long getDirLength(final String dirPath) {
        return getDirLength(getFileByPath(dirPath));
    }

    /**
     * Get the directory length
     *
     * @param dir table of Contents
     * @return Directory length
     */
    public static long getDirLength(final File dir) {
        if (!isDir(dir)) return -1;
        long len = 0;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    len += getDirLength(file);
                } else {
                    len += file.length();
                }
            }
        }
        return len;
    }

    /**
     * Get the file length
     *
     * @param filePath file Path
     * @return File length
     */
    public static long getFileLength(final String filePath) {
        return getFileLength(getFileByPath(filePath));
    }

    /**
     * Get the file length
     *
     * @param file file
     * @return File length
     */
    public static long getFileLength(final File file) {
        if (!isFile(file)) return -1;
        return file.length();
    }

    /**
     * Get the MD5 checksum of the file
     *
     * @param filePath file Path
     * @return The MD5 checksum of the file
     */
    public static String getFileMD5ToString(final String filePath) {
        File file = isSpace(filePath) ? null : new File(filePath);
        return getFileMD5ToString(file);
    }

    /**
     * Get the MD5 checksum of the file
     *
     * @param file file
     * @return The MD5 checksum of the file
     */
    public static String getFileMD5ToString(final File file) {
        return bytes2HexString(getFileMD5(file));
    }

    /**
     * Get the MD5 checksum of the file
     *
     * @param filePath file Path
     * @return The MD5 checksum of the file

     */
    public static byte[] getFileMD5(final String filePath) {
        return getFileMD5(getFileByPath(filePath));
    }

    /**
     * Get the MD5 checksum of the file
     *
     * @param file file
     * @return The MD5 checksum of the file
     */
    public static byte[] getFileMD5(final File file) {
        if (file == null) return null;
        DigestInputStream dis = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            dis = new DigestInputStream(fis, md);
            byte[] buffer = new byte[1024 * 256];
            while (true) {
                if (!(dis.read(buffer) > 0)) break;
            }
            md = dis.getMessageDigest();
            return md.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(dis);
        }
        return null;
    }

    /**
     * Gets the longest directory in the full path
     *
     * @param file file
     * @return filePath longest directory
     */
    public static String getDirName(final File file) {
        if (file == null) return null;
        return getDirName(file.getPath());
    }

    /**
     * Gets the longest directory in the full path
     *
     * @param filePath file Path
     * @return filePath longest directory
     */
    public static String getDirName(final String filePath) {
        if (isSpace(filePath)) return filePath;
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? "" : filePath.substring(0, lastSep + 1);
    }

    /**
     * Gets the file name in the full path
     *
     * @param file file
     * @return file name
     */
    public static String getFileName(final File file) {
        if (file == null) return null;
        return getFileName(file.getPath());
    }

    /**
     * Gets the file name in the full path
     *
     * @param filePath file Path
     * @return file name
     */
    public static String getFileName(final String filePath) {
        if (isSpace(filePath)) return filePath;
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);
    }

    /**
     * Gets the file name in the full path without the extension
     *
     * @param file file
     * @return The file name without the extension
     */
    public static String getFileNameNoExtension(final File file) {
        if (file == null) return null;
        return getFileNameNoExtension(file.getPath());
    }

    /**
     * Gets the file name in the full path without the extension
     *
     * @param filePath file Path
     * @return The file name without the extension
     */
    public static String getFileNameNoExtension(final String filePath) {
        if (isSpace(filePath)) return filePath;
        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastSep == -1) {
            return (lastPoi == -1 ? filePath : filePath.substring(0, lastPoi));
        }
        if (lastPoi == -1 || lastSep > lastPoi) {
            return filePath.substring(lastSep + 1);
        }
        return filePath.substring(lastSep + 1, lastPoi);
    }

    /**
     * Gets the file extension in the full path
     *
     * @param file file
     * @return File extension

     */
    public static String getFileExtension(final File file) {
        if (file == null) return null;
        return getFileExtension(file.getPath());
    }

    /**
     * Gets the file extension in the full path
     *
     * @param filePath file Path
     * @return File extension
     */
    public static String getFileExtension(final String filePath) {
        if (isSpace(filePath)) return filePath;
        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastPoi == -1 || lastSep >= lastPoi) return "";
        return filePath.substring(lastPoi + 1);
    }

    ///////////////////////////////////////////////////////////////////////////
    // copy from ConvertUtils
    ///////////////////////////////////////////////////////////////////////////

    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * byteArr to hexString
     * <p>E.g：</p>
     * bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns 00A8
     *
     * @param bytes Byte array
     * @return Hexadecimal uppercase string
     */
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

    /**
     * The number of bytes is transferred to the appropriate memory size
     * <p>Reserved 3 decimal places</p>
     *
     * @param byteNum The number of bytes
     * @return Appropriate memory size
     */
    @SuppressLint("DefaultLocale")
    private static String byte2FitMemorySize(final long byteNum) {
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        } else if (byteNum < 1024) {
            return String.format("%.3fB", (double) byteNum);
        } else if (byteNum < 1048576) {
            return String.format("%.3fKB", (double) byteNum / 1024);
        } else if (byteNum < 1073741824) {
            return String.format("%.3fMB", (double) byteNum / 1048576);
        } else {
            return String.format("%.3fGB", (double) byteNum / 1073741824);
        }
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

    public interface OnReplaceListener {
        boolean onReplace();
    }
}
