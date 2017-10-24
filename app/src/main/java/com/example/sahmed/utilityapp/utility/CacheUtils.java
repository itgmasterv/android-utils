package com.example.sahmed.utilityapp.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v4.util.SimpleArrayMap;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/05/24
 *     desc  : Cache related tool class
 * </pre>
 */
public class CacheUtils {

    private static final long DEFAULT_MAX_SIZE  = Long.MAX_VALUE;
    private static final int  DEFAULT_MAX_COUNT = Integer.MAX_VALUE;

    public static final int SEC  = 1;
    public static final int MIN  = 60;
    public static final int HOUR = 3600;
    public static final int DAY  = 86400;

    private static final SimpleArrayMap<String, CacheUtils> CACHE_MAP = new SimpleArrayMap<>();
    private CacheManager mCacheManager;

    /**
     * Get cache instance
     * <p>in/data/data/com.xxx.xxx/cache/cacheUtilstable of Contents</p>
     * <p>Cache size is limited</p>
     * <p>The number of caches is unlimited</p>
     *
     * @return {@link CacheUtils}
     */
    public static CacheUtils getInstance(Context context) {
        return getInstance("", DEFAULT_MAX_SIZE, DEFAULT_MAX_COUNT,context);
    }

    /**
     * Get cache instance
     * <p>in/data/data/com.xxx.xxx/cache/cacheName table of Contents</p>
     * <p>Cache size is limited</p>
     * <p>The number of caches is unlimited</p>
     *
     * @param cacheName Cache directory name
     * @return {@link CacheUtils}
     */
    public static CacheUtils getInstance(final String cacheName , Context context) {
        return getInstance(cacheName, DEFAULT_MAX_SIZE, DEFAULT_MAX_COUNT,context);
    }

    /**
     * Get cache instance
     * <p>in/data/data/com.xxx.xxx/cache/cacheUtils table of Contents
     </p>
     *
     * @param maxSize  Maximum cache size，Unit bytes

     * @param maxCount Maximum number of caches
     * @return {@link CacheUtils}
     */
    public static CacheUtils getInstance(final long maxSize, final int maxCount,Context context) {
        return getInstance("", maxSize, maxCount,context);
    }

    /**
     * Get cache instance
     * <p>in/data/data/com.xxx.xxx/cache/cacheName table of Contents</p>
     *
     * @param cacheName Cache directory name
     * @param maxSize   Maximum cache size, in bytes
     * @param maxCount  Maximum number of caches
     * @return {@link CacheUtils}
     */
    public static CacheUtils getInstance(String cacheName, final long maxSize, final int maxCount, Context context) {
        if (isSpace(cacheName)) cacheName = "cacheUtils";
        File file = new File(context.getCacheDir(), cacheName);
        return getInstance(file, maxSize, maxCount);
    }

    /**
     * Get cache instance
     * <p>In the cacheDir directory</p>
     * <p> Cache size is limited </ p>
     * <p> The number of caches is limited </ p>
     *
     * @param cacheDir Cache directory
     * @return {@link CacheUtils}
     */
    public static CacheUtils getInstance(@NonNull final File cacheDir) {
        return getInstance(cacheDir, DEFAULT_MAX_SIZE, DEFAULT_MAX_COUNT);
    }

    /**
     * Get cache instance
     * <p> in the cacheDir directory </ p>
     *
     * @param cacheDir cache directory
     * @param maxSize maximum cache size, in bytes
     * @param maxCount Maximum number of caches
     * @return {@link CacheUtils}
     */
    public static CacheUtils getInstance(@NonNull final File cacheDir, final long maxSize, final int maxCount) {
        final String cacheKey = cacheDir.getAbsoluteFile() + "_" + Process.myPid();
        CacheUtils cache = CACHE_MAP.get(cacheKey);
        if (cache == null) {
            cache = new CacheUtils(cacheDir, maxSize, maxCount);
            CACHE_MAP.put(cacheKey, cache);
        }
        return cache;
    }

    private CacheUtils(@NonNull final File cacheDir, final long maxSize, final int maxCount) {
        if (!cacheDir.exists() && !cacheDir.mkdirs()) {
            throw new RuntimeException("can't make dirs in " + cacheDir.getAbsolutePath());
        }
        mCacheManager = new CacheManager(cacheDir, maxSize, maxCount);
    }

    ///////////////////////////////////////////////////////////////////////////
    // bytes Read and write
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Write an array of bytes in the cache
     *
     * @param key key
     * @param value value
     */
    public void put(@NonNull final String key, @NonNull final byte[] value) {
        put(key, value, -1);
    }

    /**
     * Write an array of bytes in the cache
     *
     * @param key      key
     * @param value    value
     * @param saveTime Save time in seconds

     */
    public void put(@NonNull final String key, @NonNull byte[] value, final int saveTime) {
        if (value.length <= 0) return;
        if (saveTime >= 0) value = CacheHelper.newByteArrayWithTime(saveTime, value);
        File file = mCacheManager.getFileBeforePut(key);
        CacheHelper.writeFileFromBytes(file, value);
        mCacheManager.updateModify(file);
        mCacheManager.put(file);

    }

    /**
     * Read the byte array in the cache
     *
     * @param key key
     * @return Exists and does not expire to return the corresponding value, otherwise it returns {@code null}
     */
    public byte[] getBytes(@NonNull final String key) {
        return getBytes(key, null);
    }

    /**
     * Read the byte array in the cache
     *
     * @param key          key
     * @param defaultValue Defaults
     * @return exists and does not expire to return the corresponding value, otherwise returns the default value {@code defaultValue}
     */
    public byte[] getBytes(@NonNull final String key, final byte[] defaultValue) {
        final File file = mCacheManager.getFileIfExists(key);
        if (file == null) return defaultValue;
        byte[] data = CacheHelper.readFile2Bytes(file);
        if (CacheHelper.isDue(data)) {
            mCacheManager.removeByKey(key);
            return defaultValue;
        }
        mCacheManager.updateModify(file);
        return CacheHelper.getDataWithoutDueTime(data);
    }

    ///////////////////////////////////////////////////////////////////////////
    // String Read and write
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Write String in cache
     *
     * @param key   key
     * @param value value
     */
    public void put(@NonNull final String key, @NonNull final String value) {
        put(key, value, -1);
    }

    /**
     * Write String in cache
     *
     * @param key      key
     * @param value    value
     * @param saveTime save time in seconds
     */
    public void put(@NonNull final String key, @NonNull final String value, final int saveTime) {
        put(key, CacheHelper.string2Bytes(value), saveTime);
    }

    /**
     * Read the String in the cache
     *
     * @param key key
     * @return exists and does not expire to return the corresponding value, otherwise it returns {@code null}
     */
    public String getString(@NonNull final String key) {
        return getString(key, null);
    }

    /**
     * Read the String in the cache
     *
     * @param key          key
     * @param defaultValue Defaults
     * @return exists and does not expire to return the corresponding value, otherwise returns the default value {@code defaultValue}
     */
    public String getString(@NonNull final String key, final String defaultValue) {
        byte[] bytes = getBytes(key);
        if (bytes == null) return defaultValue;
        return CacheHelper.bytes2String(bytes);
    }

    ///////////////////////////////////////////////////////////////////////////
    // JSONObject Read and write
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Write JSONObject in cache
     *
     * @param key   key
     * @param value value
     */
    public void put(@NonNull final String key, @NonNull final JSONObject value) {
        put(key, value, -1);
    }

    /**
     * Write JSONObject in cache
     *
     * @param key      key
     * @param value    value
     * @param saveTime save time in seconds
     */
    public void put(@NonNull final String key, @NonNull final JSONObject value, final int saveTime) {
        put(key, CacheHelper.jsonObject2Bytes(value), saveTime);
    }

    /**
     * Read JSONObject in cache
     *
     * @param key key
     * @return exists and does not expire to return the corresponding value, otherwise it returns {@code null}
     */
    public JSONObject getJSONObject(@NonNull final String key) {
        return getJSONObject(key, null);
    }

    /**
     * Read JSONObject in cache
     *
     * @param key          key
     * @param defaultValue Defaults
     * @return exists and does not expire to return the corresponding value, otherwise returns the default value {@code defaultValue}
     */
    public JSONObject getJSONObject(@NonNull final String key, final JSONObject defaultValue) {
        byte[] bytes = getBytes(key);
        if (bytes == null) return defaultValue;
        return CacheHelper.bytes2JSONObject(bytes);
    }


    ///////////////////////////////////////////////////////////////////////////
    // JSONArray Read and write
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Write the JSONArray in the cache
     *
     * @param key   key
     * @param value value
     */
    public void put(@NonNull final String key, @NonNull final JSONArray value) {
        put(key, value, -1);
    }

    /**
     * Write the JSONArray in the cache
     *
     * @param key      key
     * @param value    value
     * @param saveTime Save time in seconds
     */
    public void put(@NonNull final String key, @NonNull final JSONArray value, final int saveTime) {
        put(key, CacheHelper.jsonArray2Bytes(value), saveTime);
    }

    /**
     * Read the JSONArray in the cache
     *
     * @param key key
     * @return exists and does not expire to return the corresponding value, otherwise it returns {@code null}
     */
    public JSONArray getJSONArray(@NonNull final String key) {
        return getJSONArray(key, null);
    }

    /**
     * Read the JSONArray in the cache
     *
     * @param key          key
     * @param defaultValue Defaults
     * @return exists and does not expire to return the corresponding value, otherwise returns the default value {@code defaultValue}
     */
    public JSONArray getJSONArray(@NonNull final String key, final JSONArray defaultValue) {
        byte[] bytes = getBytes(key);
        if (bytes == null) return defaultValue;
        return CacheHelper.bytes2JSONArray(bytes);
    }


    ///////////////////////////////////////////////////////////////////////////
    // Bitmap Read and write
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Write Bitmap in cache
     *
     * @param key   key
     * @param value value
     */
    public void put(@NonNull final String key, @NonNull final Bitmap value) {
        put(key, value, -1);
    }

    /**
     * Write Bitmap in cache
     *
     * @param key      key
     * @param value    value
     * @param saveTime save time in seconds
     */
    public void put(@NonNull final String key, @NonNull final Bitmap value, final int saveTime) {
        put(key, CacheHelper.bitmap2Bytes(value), saveTime);
    }

    /**
     * Read Bitmap in cache
     *
     * @param key key
     * @return exists and does not expire to return the corresponding value, otherwise it returns {@code null}
     */
    public Bitmap getBitmap(@NonNull final String key) {
        return getBitmap(key, null);
    }

    /**
     * Read Bitmap in cache
     *
     * @param key          key
     * @param defaultValue Defaults
     * @return exists and does not expire to return the corresponding value, otherwise returns the default value {@code defaultValue}
     */
    public Bitmap getBitmap(@NonNull final String key, final Bitmap defaultValue) {
        byte[] bytes = getBytes(key);
        if (bytes == null) return defaultValue;
        return CacheHelper.bytes2Bitmap(bytes);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Drawable Read and write
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Write in the cache
     *
     * @param key   key
     * @param value value
     */
    public void put(@NonNull final String key, @NonNull final Drawable value) {
        put(key, CacheHelper.drawable2Bytes(value));
    }

    /**
     * Write in the cache
     *
     * @param key      key
     * @param value    value
     * @param saveTime save time in seconds
     */
    public void put(@NonNull final String key, @NonNull final Drawable value, final int saveTime) {
        put(key, CacheHelper.drawable2Bytes(value), saveTime);
    }

    /**
     * Read in the cache
     *
     * @param key key
     * @return exists and does not expire to return the corresponding value, otherwise it returns {@code null}
     */
    public Drawable getDrawable(@NonNull final String key,Context context) {
        return getDrawable(key, null);
    }

    /**
     * Read in the cache
     *
     * @param key          key
     * @param defaultValue Defaults
     * @return exists and does not expire to return the corresponding value, otherwise returns the default value {@code defaultValue}
     */
    public Drawable getDrawable(@NonNull final String key, final Drawable defaultValue,Context context) {
        byte[] bytes = getBytes(key);
        if (bytes == null) return defaultValue;
        return CacheHelper.bytes2Drawable(bytes,context);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Parcelable Read and write

    ///////////////////////////////////////////////////////////////////////////

    /**
     * Write to Parcelable in cache
     *
     * @param key   key
     * @param value value
     */
    public void put(@NonNull final String key, @NonNull final Parcelable value) {
        put(key, value, -1);
    }

    /**
     * Write the Parcelable in the cache
     *
     * @param key      key
     * @param value    value
     * @param saveTime Save time in seconds
     */
    public void put(@NonNull final String key, @NonNull final Parcelable value, final int saveTime) {
        put(key, CacheHelper.parcelable2Bytes(value), saveTime);
    }

    /**
     * Read the Parcelable in the cache
     *
     * @param key     key
     * @param creator Builder
     * @return exists and does not expire to return the corresponding value, otherwise it returns {@code null}
     */
    public <T> T getParcelable(@NonNull final String key, @NonNull final Parcelable.Creator<T> creator) {
        return getParcelable(key, creator, null);
    }

    /**
     * Read the Parcelable in the cache
     *
     * @param key          key
     * @param creator      Builder
     * @param defaultValue Defaults
     * @return exists and does not expire to return the corresponding value, otherwise returns the default value {@code defaultValue}
     */
    public <T> T getParcelable(@NonNull final String key, @NonNull final Parcelable.Creator<T> creator, final T defaultValue) {
        byte[] bytes = getBytes(key);
        if (bytes == null) return defaultValue;
        return CacheHelper.bytes2Parcelable(bytes, creator);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Serializable Read and write
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Cache is written to Serializable
     *
     * @param key   key
     * @param value value
     */
    public void put(@NonNull final String key, @NonNull final Serializable value) {
        put(key, value, -1);
    }

    /**
     * Cache is written to Serializable
     *
     * @param key      key
     * @param value    value
     * @param saveTime save time in seconds
     */
    public void put(@NonNull final String key, @NonNull final Serializable value, final int saveTime) {
        put(key, CacheHelper.serializable2Bytes(value), saveTime);
    }

    /**
     * Cache read Serializable
     *
     * @param key key
     * @return exists and does not expire to return the corresponding value, otherwise it returns {@code null}
     */
    public Object getSerializable(@NonNull final String key) {
        return getSerializable(key, null);
    }

    /**
     * Read in the cache Serializable
     *
     * @param key          key
     * @param defaultValue Defaults
     * @return exists and does not expire to return the corresponding value, otherwise returns the default value {@code defaultValue}
     */
    public Object getSerializable(@NonNull final String key, final Object defaultValue) {
        byte[] bytes = getBytes(key);
        if (bytes == null) return defaultValue;
        return CacheHelper.bytes2Object(getBytes(key));
    }

    /**
     * Get cache size
     * <p> units: bytes </ p>
     * <p> call Thread.join (), need to be called asynchronously, otherwise the main thread may be Caton </ p>
     *
     * @return cache size
     */
    public long getCacheSize() {
        return mCacheManager.getCacheSize();
    }

    /**
     * Get the number of caches
     * <p> call Thread.join (), need to be called asynchronously, otherwise the main thread may be Caton </ p>
     *
     * @return the number of caches
     */
    public int getCacheCount() {
        return mCacheManager.getCacheCount();
    }

    /**
     * Remove the cache based on key values
     *
     * @param key key
     * @return {@code true}: Remove Success <br> {@code false}: Removal failed
     */
    public boolean remove(@NonNull final String key) {
        return mCacheManager.removeByKey(key);
    }

    /**
     * Clear all caches
     *
     * @return {@code true}: Clear Success <br> {@code false}: Clear Failed
     */
    public boolean clear() {
        return mCacheManager.clear();
    }

    private class CacheManager {
        private final AtomicLong cacheSize;
        private final AtomicInteger cacheCount;
        private final long          sizeLimit;
        private final int           countLimit;
        private final Map<File, Long> lastUsageDates = Collections.synchronizedMap(new HashMap<File, Long>());
        private final File cacheDir;
        private final Thread mThread;

        private CacheManager(final File cacheDir, final long sizeLimit, final int countLimit) {
            this.cacheDir = cacheDir;
            this.sizeLimit = sizeLimit;
            this.countLimit = countLimit;
            cacheSize = new AtomicLong();
            cacheCount = new AtomicInteger();
            mThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    int size = 0;
                    int count = 0;
                    final File[] cachedFiles = cacheDir.listFiles();
                    if (cachedFiles != null) {
                        for (File cachedFile : cachedFiles) {
                            size += cachedFile.length();
                            count += 1;
                            lastUsageDates.put(cachedFile, cachedFile.lastModified());
                        }
                        cacheSize.getAndAdd(size);
                        cacheCount.getAndAdd(count);
                    }
                }
            });
            mThread.start();
        }

        private long getCacheSize() {
            try {
                mThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return cacheSize.get();
        }

        private int getCacheCount() {
            try {
                mThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return cacheCount.get();
        }

        private File getFileBeforePut(final String key) {
            File file = new File(cacheDir, String.valueOf(key.hashCode()));
            if (file.exists()) {
                cacheCount.addAndGet(-1);
                cacheSize.addAndGet(-file.length());
            }
            return file;
        }

        private File getFileIfExists(final String key) {
            File file = new File(cacheDir, String.valueOf(key.hashCode()));
            if (!file.exists()) return null;
            return file;
        }

        private void put(final File file) {
            cacheCount.addAndGet(1);
            cacheSize.addAndGet(file.length());
            while (cacheCount.get() > countLimit || cacheSize.get() > sizeLimit) {
                cacheSize.addAndGet(-removeOldest());
                cacheCount.addAndGet(-1);
            }
        }

        private void updateModify(final File file) {
            Long millis = System.currentTimeMillis();
            file.setLastModified(millis);
            lastUsageDates.put(file, millis);
        }

        private boolean removeByKey(final String key) {
            File file = getFileIfExists(key);
            if (file == null) return true;
            if (!file.delete()) return false;
            cacheSize.addAndGet(-file.length());
            cacheCount.addAndGet(-1);
            lastUsageDates.remove(file);
            return true;
        }

        private boolean clear() {
            File[] files = cacheDir.listFiles();
            if (files == null || files.length <= 0) return true;
            boolean flag = true;
            for (File file : files) {
                if (!file.delete()) {
                    flag = false;
                    continue;
                }
                cacheSize.addAndGet(-file.length());
                cacheCount.addAndGet(-1);
                lastUsageDates.remove(file);
            }
            if (flag) {
                lastUsageDates.clear();
                cacheSize.set(0);
                cacheCount.set(0);
            }
            return flag;
        }

        /**
         * Remove old files
         *
         * @return the number of bytes removed
         */
        private long removeOldest() {
            if (lastUsageDates.isEmpty()) return 0;
            Long oldestUsage = Long.MAX_VALUE;
            File oldestFile = null;
            Set<Map.Entry<File, Long>> entries = lastUsageDates.entrySet();
            synchronized (lastUsageDates) {
                for (Map.Entry<File, Long> entry : entries) {
                    Long lastValueUsage = entry.getValue();
                    if (lastValueUsage < oldestUsage) {
                        oldestUsage = lastValueUsage;
                        oldestFile = entry.getKey();
                    }
                }
            }
            if (oldestFile == null) return 0;
            long fileSize = oldestFile.length();
            if (oldestFile.delete()) {
                lastUsageDates.remove(oldestFile);
                return fileSize;
            }
            return 0;
        }
    }

    private static class CacheHelper {

        static final int timeInfoLen = 14;

        private static byte[] newByteArrayWithTime(final int second, final byte[] data) {
            byte[] time = createDueTime(second).getBytes();
            byte[] content = new byte[time.length + data.length];
            System.arraycopy(time, 0, content, 0, time.length);
            System.arraycopy(data, 0, content, time.length, data.length);
            return content;
        }

        /**
         * Create expiration time
         *
         * @param second seconds
         * @return _ $ millis $ _
         */
        private static String createDueTime(final int second) {
            return String.format(Locale.getDefault(), "_$%010d$_", System.currentTimeMillis() / 1000 + second);
        }

        private static boolean isDue(final byte[] data) {
            long millis = getDueTime(data);
            return millis != -1 && System.currentTimeMillis() > millis;
        }

        private static long getDueTime(final byte[] data) {
            if (hasTimeInfo(data)) {
                String millis = new String(copyOfRange(data, 2, 12));
                try {
                    return Long.parseLong(millis) * 1000;
                } catch (NumberFormatException e) {
                    return -1;
                }
            }
            return -1;
        }

        private static byte[] getDataWithoutDueTime(final byte[] data) {
            if (hasTimeInfo(data)) {
                return copyOfRange(data, timeInfoLen, data.length);
            }
            return data;
        }

        private static byte[] copyOfRange(final byte[] original, final int from, final int to) {
            int newLength = to - from;
            if (newLength < 0) throw new IllegalArgumentException(from + " > " + to);
            byte[] copy = new byte[newLength];
            System.arraycopy(original, from, copy, 0, Math.min(original.length - from, newLength));
            return copy;
        }

        private static boolean hasTimeInfo(final byte[] data) {
            return data != null
                    && data.length >= timeInfoLen
                    && data[0] == '_'
                    && data[1] == '$'
                    && data[12] == '$'
                    && data[13] == '_';
        }

        private static void writeFileFromBytes(final File file, final byte[] bytes) {
            FileChannel fc = null;
            try {
                fc = new FileOutputStream(file, false).getChannel();
                fc.write(ByteBuffer.wrap(bytes));
                fc.force(true);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                CloseUtils.closeIO(fc);
            }
        }

        private static byte[] readFile2Bytes(final File file) {
            FileChannel fc = null;
            try {
                fc = new RandomAccessFile(file, "r").getChannel();
                int size = (int) fc.size();
                MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, size).load();
                byte[] data = new byte[size];
                mbb.get(data, 0, size);
                return data;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                CloseUtils.closeIO(fc);
            }
        }

        private static byte[] string2Bytes(final String string) {
            if (string == null) return null;
            return string.getBytes();
        }

        private static String bytes2String(final byte[] bytes) {
            if (bytes == null) return null;
            return new String(bytes);
        }

        private static byte[] jsonObject2Bytes(final JSONObject jsonObject) {
            if (jsonObject == null) return null;
            return jsonObject.toString().getBytes();
        }

        private static JSONObject bytes2JSONObject(final byte[] bytes) {
            if (bytes == null) return null;
            try {
                return new JSONObject(new String(bytes));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private static byte[] jsonArray2Bytes(final JSONArray jsonArray) {
            if (jsonArray == null) return null;
            return jsonArray.toString().getBytes();
        }

        private static JSONArray bytes2JSONArray(final byte[] bytes) {
            if (bytes == null) return null;
            try {
                return new JSONArray(new String(bytes));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private static byte[] parcelable2Bytes(final Parcelable parcelable) {
            if (parcelable == null) return null;
            Parcel parcel = Parcel.obtain();
            parcelable.writeToParcel(parcel, 0);
            byte[] bytes = parcel.marshall();
            parcel.recycle();
            return bytes;
        }

        private static <T> T bytes2Parcelable(final byte[] bytes, final Parcelable.Creator<T> creator) {
            if (bytes == null) return null;
            Parcel parcel = Parcel.obtain();
            parcel.unmarshall(bytes, 0, bytes.length);
            parcel.setDataPosition(0);
            T result = creator.createFromParcel(parcel);
            parcel.recycle();
            return result;
        }

        private static byte[] serializable2Bytes(final Serializable serializable) {
            if (serializable == null) return null;
            ByteArrayOutputStream baos;
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(baos = new ByteArrayOutputStream());
                oos.writeObject(serializable);
                return baos.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                CloseUtils.closeIO(oos);
            }
        }

        private static Object bytes2Object(final byte[] bytes) {
            if (bytes == null) return null;
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
                return ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                CloseUtils.closeIO(ois);
            }
        }

        private static byte[] bitmap2Bytes(final Bitmap bitmap) {
            if (bitmap == null) return null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            return baos.toByteArray();
        }

        private static Bitmap bytes2Bitmap(final byte[] bytes) {
            return (bytes == null || bytes.length == 0) ? null : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }

        private static byte[] drawable2Bytes(final Drawable drawable) {
            return drawable == null ? null : bitmap2Bytes(drawable2Bitmap(drawable));
        }

        private static Drawable bytes2Drawable(final byte[] bytes,Context context) {
            return bytes == null ? null : bitmap2Drawable(bytes2Bitmap(bytes),context);
        }

        private static Bitmap drawable2Bitmap(final Drawable drawable) {
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

        private static Drawable bitmap2Drawable(final Bitmap bitmap,Context context) {
            return bitmap == null ? null : new BitmapDrawable(context.getResources(), bitmap);
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
}
