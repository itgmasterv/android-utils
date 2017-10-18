package com.example.sahmed.utilityapp.utility;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sahmed on 10/17/2017.
 */

public class ServiceUtils {


    private ServiceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Get all running services
     *
     * @param context context of application
     * @return service name collection
     */
    public static Set getAllRunningService(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> info = activityManager.getRunningServices(0x7FFFFFFF);
        Set<String> names = new HashSet<>();
        if (info == null || info.size() == 0) return null;
        for (ActivityManager.RunningServiceInfo aInfo : info) {
            names.add(aInfo.service.getClassName());
        }
        return names;
    }

    /**
     * Start the service
     *
     * @param context   context of application
     * @param className The service class name of the full package name
     */
    public static void startService(final String className, Context context) {
        try {
            startService(Class.forName(className), context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Start the service
     *
     * @param context context of application
     * @param cls     service class
     */
    public static void startService(final Class<?> cls, Context context) {
        Intent intent = new Intent(context, cls);
        context.startService(intent);
    }

    /**
     * Out of service
     *
     * @param className The service class name of the full package name
     * @param context   context of application
     * @return {@code true}: Stop Success <br> {@code false}: Stop Failed
     */
    public static boolean stopService(final String className, Context context) {
        try {
            return stopService(Class.forName(className), context);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Out of service
     *
     * @param cls     service class
     * @param context context of application
     * @return {@code true}: Stop Success <br> {@code false}: Stop Failed
     */
    public static boolean stopService(final Class<?> cls, Context context) {
        Intent intent = new Intent(context, cls);
        return context.stopService(intent);
    }

    /**
     * Determine whether the service is running
     *
     * @param className The service class name of the full package name
     * @param context   context of application
     * @return {@code true}: Yes {@code false}: No
     */
    public static boolean isServiceRunning(final String className, Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> info = activityManager.getRunningServices(0x7FFFFFFF);
        if (info == null || info.size() == 0) return false;
        for (ActivityManager.RunningServiceInfo aInfo : info) {
            if (className.equals(aInfo.service.getClassName())) return true;
        }
        return false;
    }

}

