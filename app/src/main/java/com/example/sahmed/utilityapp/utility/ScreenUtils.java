package com.example.sahmed.utilityapp.utility;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.view.Surface;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by sahmed on 10/17/2017.
 */

public class ScreenUtils {


    private ScreenUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Get the width of the screen (unit: px)
     *
     * @return screen wide
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * Get the height of the screen (unit: px)
     *
     * @return screen high
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * Set the screen to full screen
     * <p> need to call </ p> before {@code setContentView}
     *
     * @param activity activity
     */
    public static void setFullScreen(@NonNull final Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * Set the screen to horizontal screen
     * <p>
     * <p> There is a kind of attribute in the Activity android: screenOrientation = "landscape" </ p>
     * <p> does not set the Activity android: configChanges, the cut screen will re-call the various life cycle, cut horizontal screen will be executed once, cut the screen will be executed twice </ p>
     * <p> Set the Activity android: configChanges = "orientation", the cut screen or will re-call the various life cycle, cut, vertical screen will only be executed once </ p>
     * <p> set the Activity android: configChanges = "orientation | keyboardHidden | screenSize" (4.0 must have the last argument)
     * Cut the screen will not re-call each life cycle, only the implementation of onConfigurationChanged method </ p>
     *
     * @param activity activity
     */
    public static void setLandscape(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * Set the screen to vertical screen
     *
     * @param activity activity
     */
    public static void setPortrait(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * Judge whether the horizontal screen
     *
     * @return {@code true}: Yes {@code false}: No
     */
    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * Determine whether the vertical screen
     *
     * @return {@code true}: Yes {@code false}: No
     */
    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * Get the screen rotation angle
     *
     * @param activity activity
     * @return screen rotation angle
     */
    public static int getScreenRotation(@NonNull final Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            default:
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
        }
    }

}
