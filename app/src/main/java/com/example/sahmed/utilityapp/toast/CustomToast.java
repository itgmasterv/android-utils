package com.example.sahmed.utilityapp.toast;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sahmed.utilityapp.R;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/08/31
 *     desc  : Customize Toast
 * </pre>
 */
public class CustomToast {

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    /**
     * Show the view or text notification for a short period of time.  This time
     * could be user-definable.  This is the default.
     *
     * @param text The text to show.  Can be formatted text.
     */
    public static void showShortToast(@NonNull final CharSequence text , Context context) {
        show(text, Toast.LENGTH_SHORT,context);
    }

    /**
     * Show the view or text notification for a short period of time.  This time
     * could be user-definable.  This is the default.
     *
     * @param resId The resource id of the string resource to use.  Can be formatted text.
     */
    public static void showShortToast(Context context,@StringRes final int resId) {
        show(context.getResources().getString(resId), Toast.LENGTH_SHORT,context);
    }

    /**
     * Show the view or text notification for a short period of time.  This time
     * could be user-definable.  This is the default.
     *
     * @param resId The resource id of the string resource to use.  Can be formatted text.
     * @param args  parameter
     */

    public static void showShortToast(Context context,@StringRes final int resId, final Object... args) {
        show (context,resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * Show the view or text notification for a short period of time.  This time
     * could be user-definable.  This is the default.
     *
     * @param format The text to show.  Can be formatted text.
     * @param args   parameter
     */

    public static void showShortToast(Context context,final String format,final Object... args) {
        show(String.format(format, args), Toast.LENGTH_SHORT, context);
    }

    /**
     * Show the view or text notification for a long period of time.  This time
     * could be user-definable.
     *
     * @param text The text to show.  Can be formatted text.
     */

    public static void showLongToast(@NonNull final CharSequence text,Context context) {
            show(text, Toast.LENGTH_LONG,context);
    }

    /**
     * Show the view or text notification for a long period of time.  This time
     * could be user-definable.
     *
     * @param resId The resource id of the string resource to use.  Can be formatted text.
     */

    public static void showLongToast(Context context,@StringRes final int resId) {
        show(context.getResources().getString(resId), Toast.LENGTH_LONG,context);
    }

    /**
     * Show the view or text notification for a long period of time.  This time
     * could be user-definable.
     *
     * @param resId The resource id of the string resource to use.  Can be formatted text.
     * @param args  parameter
     */

    public static void showLongToast(Context context,@StringRes final int resId, final Object... args) {
        show(context,resId, Toast.LENGTH_LONG, args,context);
    }

    /**
     * Show the view or text notification for a long period of time.  This time
     * could be user-definable.
     *
     * @param format The text to show.  Can be formatted text.
     * @param args   parameter
     */

    public static void showLongToast(Context context,final String format, final Object... args) {
        show(String.format(format, args), Toast.LENGTH_LONG, context);
    }

    /**
     * Show the toast for the specified duration.
     *
     * @param resId    The resource id of the string resource to use.  Can be formatted text.
     * @param duration How long to display the message.
     */


    private static void show(Context context,@StringRes final int resId, final int duration) {
        show(context.getResources().getString(resId), duration,context);
    }

    /**
     * Show the toast for the specified duration.
     *
     * @param resId    The resource id of the string resource to use.  Can be formatted text.
     * @param duration How long to display the message.
     * @param args     parameter
     */

    private static void show(Context context,@StringRes final int resId, final int duration, final Object... args) {
        show(String.format(context.getResources().getString(resId), args), duration ,context);
    }

    /**
     * Show the toast for the specified duration.
     *
     * @param format   The text to show.  Can be formatted text.
     * @param duration How long to display the message.
     * @param args     parameter
     */

    private static void showfinal (Context context, String format, final int duration, final Object... args) {
        show(String.format(format, args), duration,context);
    }

    /**
     * Make a standard toast that just contains a text view.
     *
     * @param text     The text to show.  Can be formatted text.
     * @param duration How long to display the message.
     *
     */

    private static void show(final CharSequence text, final int duration , final Context context) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                TextView toastView;
                if (duration == Toast.LENGTH_SHORT) {
                    toastView = (TextView) ToastUtils.showCustomShort(R.layout.toast_custom,context);
                } else {
                    toastView = (TextView) ToastUtils.showCustomLong(R.layout.toast_custom,context);
                }
                toastView.setText(text);
            }
        });
    }

    public static void cancel() {
        ToastUtils.cancel();
    }
}
