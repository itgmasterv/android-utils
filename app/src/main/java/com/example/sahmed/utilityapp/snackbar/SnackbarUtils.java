package com.example.sahmed.utilityapp.snackbar;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/16
 *     desc  : Snackbar related tools
 *
 * </pre>
 */
public final class SnackbarUtils {

    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_SHORT = -1;
    public static final int LENGTH_LONG = 0;
    private static final int COLOR_DEFAULT = 0xFEFFFFFF;
    private static final int COLOR_SUCCESS = 0xFF2BB600;
    private static final int COLOR_WARNING = 0xFFFFC100;
    private static final int COLOR_ERROR = 0xFFFF0000;
    private static final int COLOR_MESSAGE = 0xFFFFFFFF;
    private static WeakReference<Snackbar> snackbarWeakReference;
    private View parent;
    private CharSequence message;
    private int messageColor;
    private int bgColor;
    private int bgResource;
    private int duration;
    private CharSequence actionText;
    private int actionTextColor;
    private View.OnClickListener actionListener;
    private int bottomMargin;

    private SnackbarUtils(final View parent) {
        setDefault();
        this.parent = parent;
    }

    /**
     * Set snackbar to depend on view
     *
     * @param parent the view to find a parent from.
     * @return {@link SnackbarUtils}
     */
    public static SnackbarUtils with(@NonNull final View parent) {
        return new SnackbarUtils(parent);
    }

    /**
     * Disappear snackbar
     */
    public static void dismiss() {
        if (snackbarWeakReference != null && snackbarWeakReference.get() != null) {
            snackbarWeakReference.get().dismiss();
            snackbarWeakReference = null;
        }
    }

    /**
     * Get the snackbar view
     *
     * @return snackbar view
     */
    public static View getView() {
        Snackbar snackbar = snackbarWeakReference.get();
        if (snackbar == null) return null;
        return snackbar.getView();
    }

    /**
     * Add the snackbar view
     *
     * @param layoutId Denotes that an integer parameter, field or method return value is expected
     *                 to be a layout resource reference
     * @param params   Layout parameters
     */
    public static void addView(@LayoutRes final int layoutId, @NonNull final ViewGroup.LayoutParams params) {
        final View view = getView();
        if (view != null) {
            view.setPadding(0, 0, 0, 0);
            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) view;
            View child = LayoutInflater.from(view.getContext()).inflate(layoutId, null);
            layout.addView(child, -1, params);
        }
    }

    /**
     * Add the snackbar view
     *
     * @param child  The view to add
     * @param params Layout parameters
     */
    public static void addView(@NonNull final View child, @NonNull final ViewGroup.LayoutParams params) {
        final View view = getView();
        if (view != null) {
            view.setPadding(0, 0, 0, 0);
            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) view;
            layout.addView(child, params);
        }
    }

    private void setDefault() {
        message = "";
        messageColor = COLOR_DEFAULT;
        bgColor = COLOR_DEFAULT;
        bgResource = -1;
        duration = LENGTH_SHORT;
        actionText = "";
        actionTextColor = COLOR_DEFAULT;
        bottomMargin = 0;
    }

    /**
     * Update the text in this {@link Snackbar}.
     *
     * @param msg The new text .
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setMessage(@NonNull final CharSequence msg) {
        this.message = msg;
        return this;
    }

    /**
     * Set the message color
     *
     * @param color A single color value .
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setMessageColor(@ColorInt final int color) {
        this.messageColor = color;
        return this;
    }

    /**
     * Set the background color associated with a snackbar.
     *
     * @param color A single color value .
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setBgColor(@ColorInt final int color) {
        this.bgColor = color;
        return this;
    }

    /**
     * Set the background color associated with a particular resource ID.
     *
     * @param bgResource Denotes that an integer parameter, field or method return value is expected
     *                   to be a drawable resource reference
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setBgResource(@DrawableRes final int bgResource) {
        this.bgResource = bgResource;
        return this;
    }

    /**
     * Make a Snackbar to display a message for the specified duration.
     *
     * @param duration How long to display the message.
     *                 <ul>
     *                 <li>{@link Duration#LENGTH_INDEFINITE}indefinite</li>
     *                 <li>{@link Duration#LENGTH_SHORT}Short time</li>
     *                 <li>{@link Duration#LENGTH_LONG}long time</li>
     *                 </ul>
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setDuration(@Duration final int duration) {
        this.duration = duration;
        return this;
    }

    /**
     * Set the action to be displayed .
     *
     * @param text     Text to display for the action
     * @param listener callback to be invoked when the action is clicked
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setAction(@NonNull final CharSequence text, @NonNull final View.OnClickListener listener) {
        return setAction(text, COLOR_DEFAULT, listener);
    }


    /**
     * Set the action to be displayed .
     *
     * @param text     Text to display for the action
     * @param color    the text color of the action specified in
     * @param listener callback to be invoked when the action is clicked
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setAction(@NonNull final CharSequence text, @ColorInt final int color, @NonNull final View.OnClickListener listener) {
        this.actionText = text;
        this.actionTextColor = color;
        this.actionListener = listener;
        return this;
    }

    /**
     * Set the bottom margin
     *
     * @param bottomMargin Bottom margin
     */
    public SnackbarUtils setBottomMargin(@IntRange(from = 1) final int bottomMargin) {
        this.bottomMargin = bottomMargin;
        return this;
    }

    /**
     * Show snackbar
     */
    public void show() {
        final View view = parent;
        if (view == null) return;
        if (messageColor != COLOR_DEFAULT) {
            SpannableString spannableString = new SpannableString(message);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(messageColor);
            spannableString.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            snackbarWeakReference = new WeakReference<>(Snackbar.make(view, spannableString, duration));
        } else {
            snackbarWeakReference = new WeakReference<>(Snackbar.make(view, message, duration));
        }
        final Snackbar snackbar = snackbarWeakReference.get();
        final View snackbarView = snackbar.getView();
        if (bgResource != -1) {
            snackbarView.setBackgroundResource(bgResource);
        } else if (bgColor != COLOR_DEFAULT) {
            snackbarView.setBackgroundColor(bgColor);
        }
        if (bottomMargin != 0) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) snackbarView.getLayoutParams();
            params.bottomMargin = bottomMargin;
        }
        if (actionText.length() > 0 && actionListener != null) {
            if (actionTextColor != COLOR_DEFAULT) {
                snackbar.setActionTextColor(actionTextColor);
            }
            snackbar.setAction(actionText, actionListener);
        }
        snackbar.show();
    }

    /**
     * Show the success of the snackbar
     */
    public void showSuccess() {
        bgColor = COLOR_SUCCESS;
        messageColor = COLOR_MESSAGE;
        actionTextColor = COLOR_MESSAGE;
        show();
    }

    /**
     * Show the default warning of snackbar
     */
    public void showWarning() {
        bgColor = COLOR_WARNING;
        messageColor = COLOR_MESSAGE;
        actionTextColor = COLOR_MESSAGE;
        show();
    }

    /**
     * Displays the default Error message of snackbar
     */
    public void showError() {
        bgColor = COLOR_ERROR;
        messageColor = COLOR_MESSAGE;
        actionTextColor = COLOR_MESSAGE;
        show();
    }

    @IntDef({LENGTH_INDEFINITE, LENGTH_SHORT, LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }
}
