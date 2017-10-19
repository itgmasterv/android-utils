package com.example.sahmed.utilityapp.utility.constants;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by sahmed on 10/18/2017.
 */

public final class TimeConstants {

    /**
     * Milliseconds and milliseconds
     */
    public static final int MSEC = 1;
    /**
     * Seconds and milliseconds
     */
    public static final int SEC  = 1000;
    /**
     * Minutes and multiple of milliseconds
     */
    public static final int MIN  = 60000;
    /**
     * With a multiple of milliseconds
     */
    public static final int HOUR = 3600000;
    /**
     * Day and milliseconds
     */
    public static final int DAY  = 86400000;

    @IntDef({MSEC, SEC, MIN, HOUR, DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}

