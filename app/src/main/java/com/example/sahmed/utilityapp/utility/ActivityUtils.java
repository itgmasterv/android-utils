package com.example.sahmed.utilityapp.utility;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by sahmed on 10/17/2017.
 */

public class ActivityUtils {


    /**
     * startActivity
     * without sending any Bundles to the other activity
     *
     * @param activity activity that i will move from it
     * @param clz      activity that i will move to it
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Class<?> clz) {

        Intent intent = new Intent(activity,clz);
        activity.startActivity(intent);

  }


    /**
     * startActivity
     * with sending  Bundles to the other activity
     *
     * @param activity activity that i will move from it
     * @param clz      activity that i will move to it
     * @param options   options data will be sent to the other activity
     */

    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Class<?> clz,
                                     @NonNull final Bundle options) {

        Intent intent = new Intent(activity,clz);
        if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            activity.startActivity(intent, options);
        } else {
            activity.startActivity(intent);
        }
    }


    /**
     * finishActivity
     *
     * @param activity activity that will be finished
     */
    public static void finishActivity(@NonNull final Activity activity) {
        activity.finish();
    }





}
