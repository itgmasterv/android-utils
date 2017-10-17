package com.example.sahmed.utilityapp.utility;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by sahmed on 10/16/2017.
 */

public final class KeyboardUtils {

    private KeyboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * showSoftInput
     * Dynamic display soft keyboard if any view is focused
     *
     * @param activity activity to get current focused view
     */
    public static void showSoftInput(final Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * showSoftInput
     * Dynamic display soft keyboard if specific view is focused
     *
     * @param view    specific view send to this method  if it isn't focused it will be focusable then show soft keyboard
     * @param context context of application
     */
    public static void showSoftInput(final View view, Context context) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * hideSoftInput
     * Dynamic hiding the soft keyboard if any view is focused
     *
     * @param activity activity to get current focused view
     */
    public static void hideSoftInput(final Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * hideSoftInput
     * Dynamic hiding the soft keyboard if specific view is focused
     *
     * @param view    specific view send to this method to hide soft keyboard
     * @param context context of application
     */
    public static void hideSoftInput(final View view, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * toggleSoftInput
     * Switch the keyboard display status if it is already displayed it will be hidden it and
     * if it is already hidden it will be displayed  it
     *
     * @param context context of application
     */
    public static void toggleSoftInput(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }


    /**
     * isShouldHideKeyboard
     * according to the coordinates of EditText and the user click on the coordinates of contrast,
     * to determine whether to hide the keyboard
     *
     * @param v     is EditText view
     * @param event describe movements in terms of an action code and a set of axis values
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }


    /**
     * isKeyboardShown
     * check if keyboard is displayed or not
     *
     * @param rootView is EditText view
     */
    private boolean isKeyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect frame = new Rect();
        rootView.getWindowVisibleDisplayFrame(frame);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - frame.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }


}

