package com.example.sahmed.utilityapp.utility;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by sahmed on 10/4/2017.
 */

public class ActivityUtils {

    /**
     * 启动Activity
     *
     * @param activity activity
     * @param clz      activity类
     */
    public static void startActivity(@NonNull final Activity activity, @NonNull final Class<?> clz) {
        startActivity(activity, null, activity.getPackageName(), clz.getName(), null);
    }

    /**
     * 结束Activity
     *
     * @param clz activity类
     */
    public static void finishActivity(@NonNull final Class<?> clz) {
        finishActivity(clz, false);
    }

    /**
     * 结束Activity
     *
     * @param clz        activity类
     * @param isLoadAnim 是否启动动画
     */
    public static void finishActivity(@NonNull final Class<?> clz, final boolean isLoadAnim) {
        List<Activity> activities = Utils.sActivityList;
        for (Activity activity : activities) {
            if (activity.getClass().equals(clz)) {
                activity.finish();
                if (!isLoadAnim) {
                    activity.overridePendingTransition(0, 0);
                }
            }
        }
    }



    /*----------------------------------------------*/
    private static void startActivity(final Context context, final Bundle extras, final String pkg, final String cls, final Bundle options) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (extras != null) intent.putExtras(extras);
        intent.setComponent(new ComponentName(pkg, cls));
        startActivity(intent, context, options);
    }

    private static void startActivity(final Intent intent, final Context context, final Bundle options) {
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivity(intent, options);
        } else {
            context.startActivity(intent);
        }
    }
}
