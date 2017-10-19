package com.example.sahmed.utilityapp.utility.constants;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by sahmed on 10/18/2017.
 */

public final class MemoryConstants {

    /**
     * Byte and Byte multiple
     */
    public static final int BYTE = 1;
    /**
     * KB and Byte multiple
     */
    public static final int KB = 1024;
    /**
     * MB and Byte multiple
     */
    public static final int MB = 1048576;
    /**
     * GB and Byte multiple
     */
    public static final int GB = 1073741824;

    @IntDef({BYTE, KB, MB, GB})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}
