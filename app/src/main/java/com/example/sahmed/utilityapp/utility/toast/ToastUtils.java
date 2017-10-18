package com.example.sahmed.utilityapp.utility.toast;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/29
 *     desc  : 吐司相关工具类
 * </pre>
 */
public final class ToastUtils {
    /**
     * Show the view or text notification for a short period of time.  This time
     * could be user-definable.  This is the default.
     */
    public static final int LENGTH_SHORT = 0;

    /**
     * Show the view or text notification for a long period of time.  This time
     * could be user-definable.
     */
    public static final int LENGTH_LONG = 1;


    private static final int COLOR_DEFAULT = 0xFEFFFFFF;
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    private static Toast sToast;
    private static WeakReference<View> sViewWeakReference;
    private static int sLayoutId = -1;
    private static int gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    private static int xOffset = 0;
    private static int bgColor = COLOR_DEFAULT;
    private static int bgResource = -1;
    private static int msgColor = COLOR_DEFAULT;

    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Set the location at which the notification should appear on the screen.
     *
     * @param gravity location at which the notification should appear on the screen.
     * @param xOffset the X offset in pixels to apply to the gravity's location.
     * @param yOffset the Y offset in pixels to apply to the gravity's location.
     */
    public static void setGravity(final int gravity, final int xOffset, final int yOffset) {
        ToastUtils.gravity = gravity;
        ToastUtils.xOffset = xOffset;
    }

    /**
     * Set the background color associated with a notification.
     *
     * @param backgroundColor A single color value .
     */

    public static void setBgColor(@ColorInt final int backgroundColor) {
        ToastUtils.bgColor = backgroundColor;
    }

    /**
     * Set the background color associated with a particular resource ID.
     *
     * @param bgResource Denotes that an integer parameter, field or method return value is expected
     *                   to be a drawable resource reference
     */
    public static void setBgResource(@DrawableRes final int bgResource) {
        ToastUtils.bgResource = bgResource;
    }

    /**
     * Set the message color
     *
     * @param msgColor Denotes that the annotated element represents a packed color
     *                 If applied to an int array, every element in the array represents a color integer.
     */
    public static void setMsgColor(@ColorInt final int msgColor) {
        ToastUtils.msgColor = msgColor;
    }

    /**
     * Show the view or text notification for a short period of time.  This time
     * could be user-definable.  This is the default.
     *
     * @param text The text to show.  Can be formatted text.
     */
    public static void showShortToast(@NonNull final CharSequence text,Context context) {
       // Log.e("TAG", "showShortToast: ");
        show(text, Toast.LENGTH_SHORT,context);
    }

    /**
     * Show the view or text notification for a short period of time.  This time
     * could be user-definable.  This is the default.
     *
     * @param resId The resource id of the string resource to use.  Can be formatted text.
     */
    public static void showShortToast(Context context, @StringRes final int resId) {
        show(context, resId, Toast.LENGTH_SHORT,context);
    }

    /**
     * Show the view or text notification for a short period of time.  This time
     * could be user-definable.  This is the default.
     *
     * @param resId The resource id of the string resource to use.  Can be formatted text.
     * @param args  parameter
     */
    public static void showShortToast(Context context, @StringRes final int resId, final Object... args) {
        show(context, resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * Show the view or text notification for a short period of time.  This time
     * could be user-definable.  This is the default.
     *
     * @param format The text to show.  Can be formatted text.
     * @param args   parameter
     */
    public static void showShortToast(Context context, final String format, final Object... args) {
        show(context, format, Toast.LENGTH_SHORT, args);
    }

    /**
     * Show the view or text notification for a long period of time.  This time
     * could be user-definable.
     *
     * @param text The text to show.  Can be formatted text.
     */
    public static void showLongToast(@NonNull final CharSequence text, Context context) {
        show(text, Toast.LENGTH_LONG, context);
    }

    /**
     * Show the view or text notification for a long period of time.  This time
     * could be user-definable.
     *
     * @param resId The resource id of the string resource to use.  Can be formatted text.
     */
    public static void showLongToast(Context context,@StringRes final int resId) {
        show(context, resId, Toast.LENGTH_LONG);
    }

    /**
     * Show the view or text notification for a long period of time.  This time
     * could be user-definable.
     *
     * @param resId The resource id of the string resource to use.  Can be formatted text.
     * @param args  parameter
     */
    public static void showLongToast(Context context,@StringRes final int resId, final Object... args) {
        show(context, resId, Toast.LENGTH_LONG, args);
    }

    /**
     * Show the view or text notification for a long period of time.  This time
     * could be user-definable.
     *
     * @param format The text to show.  Can be formatted text.
     * @param args   parameter
     */
    public static void showLongToast(Context context,final String format, final Object... args) { //format&multiobjects
        show(context, format, Toast.LENGTH_LONG, args);
    }

    /**
     * display of short-term custom toast
     *
     * @param layoutId Denotes that an integer parameter, field or method return value is expected
     *                 to be a layout resource reference
     */
    public static View showCustomShort(@LayoutRes final int layoutId,Context context) {
        final View view = getView(layoutId,context);
        show(view, Toast.LENGTH_SHORT,context);
        return view;
    }

    /**
     * display of long-term custom toast
     *
     * @param layoutId Denotes that an integer parameter, field or method return value is expected
     *                 to be a layout resource reference
     */
    public static View showCustomLong(@LayoutRes final int layoutId,Context context) {
        final View view = getView(layoutId,context);
        show(view, Toast.LENGTH_LONG,context);
        return view;
    }

    /**
     * Close the view if it's showing, or don't show it if it isn't showing yet.
     * You do not normally have to call this.  Normally view will disappear on its own
     * after the appropriate duration.
     */
    public static void cancel() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
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
        show(String.format(context.getResources().getString(resId), args), duration,context);
    }

    /**
     * Show the toast for the specified duration.
     *
     * @param format   The text to show.  Can be formatted text.
     * @param duration How long to display the message.
     * @param args     parameter
     */

    private static void show(Context context, final String format, final int duration, final Object... args) {
        show(String.format(format, args), duration, context);
    }

    /**
     * Make a standard toast that just contains a text view.
     *
     * @param text     The text to show.  Can be formatted text.
     * @param duration How long to display the message.  Either {@link #LENGTH_SHORT} or
     *                 {@link #LENGTH_LONG}
     */
    private static void show(final CharSequence text, final int duration , final Context context) {
        Log.e("TAG", "show: " );
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                cancel();
                sToast = Toast.makeText(context, text, duration);
                //solve the font of toast
                TextView tvMessage = (TextView) sToast.getView().findViewById(android.R.id.message);
                TextViewCompat.setTextAppearance(tvMessage, android.R.style.TextAppearance);
                tvMessage.setTextColor(msgColor);
                setBgAndGravity(context);
                sToast.show();
            }
        });
    }

    /**
     * Make a standard toast that just contains a text view.
     *
     * @param view     the view to show.
     * @param duration How long to display the message.  Either {@link #LENGTH_SHORT} or
     *                 {@link #LENGTH_LONG}
     */

    private static void show(final View view, final int duration, final Context context) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                cancel();
                sToast = new Toast(context);
                sToast.setView(view);
                sToast.setDuration(duration);
                setBgAndGravity(context);
                sToast.show();
            }
        });
    }

    /**
     * Gets view & the background drawable
     * @return The drawable used as the background for this view, if any.
     *
     * Set the background to a given resource. The resource should refer to
     * a Drawable object or 0 to remove the background.
     *
     * Set location at which the notification should appear on the screen.
     */

    private static void setBgAndGravity(Context mcContext) {
        View toastView = sToast.getView();
        if (bgResource != -1) {
            toastView.setBackgroundResource(bgResource);
        } else if (bgColor != COLOR_DEFAULT) {
            Drawable background = toastView.getBackground();
            background.setColorFilter(new PorterDuffColorFilter(bgColor, PorterDuff.Mode.SRC_IN));
        }

        int yOffset = (int) (64 * mcContext.getResources().getDisplayMetrics().density + 0.5);

        sToast.setGravity(gravity, xOffset, yOffset);
    }

    /**
     * Return the view.
     */
    private static View getView(@LayoutRes final int layoutId, Context context) {
        if (sLayoutId == layoutId) {
            if (sViewWeakReference != null) {
                final View toastView = sViewWeakReference.get();
                if (toastView != null) {
                    return toastView;
                }
            }
        }
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View toastView = inflate.inflate(layoutId, null);
        sViewWeakReference = new WeakReference<>(toastView);
        sLayoutId = layoutId;
        return toastView;
    }
}