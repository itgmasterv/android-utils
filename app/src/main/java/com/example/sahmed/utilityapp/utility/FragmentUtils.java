package com.example.sahmed.utilityapp.utility;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mehab on 10/22/2017.
 */

public class FragmentUtils {
    private static final int TYPE_ADD_FRAGMENT       = 0x01;
    private static final int TYPE_SHOW_FRAGMENT      = 0x01 << 1;
    private static final int TYPE_HIDE_FRAGMENT      = 0x01 << 2;
    private static final int TYPE_SHOW_HIDE_FRAGMENT = 0x01 << 3;
    private static final int TYPE_REPLACE_FRAGMENT   = 0x01 << 4;
    private static final int TYPE_REMOVE_FRAGMENT    = 0x01 << 5;
    private static final int TYPE_REMOVE_TO_FRAGMENT = 0x01 << 6;

    private static final String ARGS_ID           = "args_id";
    private static final String ARGS_IS_HIDE      = "args_is_hide";
    private static final String ARGS_IS_ADD_STACK = "args_is_add_stack";

    private FragmentUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Add fragment
     *
     * @param fm          the fragment manager
     * @param containerId Layout Id
     * @param add         The new fragment to be added
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId) {
        add(fm, add, containerId, false, false);
    }

    /**
     *  Add fragment
     *
     * @param fm          the fragment manager
     * @param containerId Layout Id
     * @param add         The new fragment to be added
     * @param isHide      Whether it is hidden
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           final boolean isHide) {
        add(fm, add, containerId, isHide, false);
    }

    /**
     * Add fragment
     *
     * @param fm          the fragment manager
     * @param containerId Layout Id
     * @param add         The new fragment to be added
     * @param isHide      Whether it is hidden
     * @param isAddStack  Whether to fall back to the stack
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           final boolean isHide,
                           final boolean isAddStack) {
        putArgs(add, new Args(containerId, isHide, isAddStack));
        operateNoAnim(fm, TYPE_ADD_FRAGMENT, null, add);
    }

    /**
     *  Add fragment
     *
     * @param fm          the fragment manager
     * @param containerId Layout Id
     * @param add         The new fragment to be added
     * @param enterAnim   Admission animation
     * @param exitAnim    Play animation
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           @AnimRes final int enterAnim,
                           @AnimRes final int exitAnim) {
        add(fm, add, containerId, false, enterAnim, exitAnim, 0, 0);
    }

    /**
     * Add fragment
     *
     * @param fm           the fragment manager
     * @param containerId Layout Id
     * @param add         The new fragment to be added
     * @param isAddStack  Whether to fall back to the stack
     * @param enterAnim   Admission animation
     * @param exitAnim    Play animation
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           final boolean isAddStack,
                           @AnimRes final int enterAnim,
                           @AnimRes final int exitAnim) {
        add(fm, add, containerId, isAddStack, enterAnim, exitAnim, 0, 0);
    }

    /**
     *  Add fragment
     *
     * @param fm           the fragment manager
     * @param containerId  Layout Id
     * @param add          The new fragment to be added
     * @param enterAnim   Admission animation
     * @param exitAnim     Play animation
     * @param popEnterAnim Stack animation
     * @param popExitAnim  Stack animation
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           @AnimRes final int enterAnim,
                           @AnimRes final int exitAnim,
                           @AnimRes final int popEnterAnim,
                           @AnimRes final int popExitAnim) {
        add(fm, add, containerId, false, enterAnim, exitAnim, popEnterAnim, popExitAnim);
    }

    /**
     * Add fragment
     *
     * @param fm          the fragment manager
     * @param containerId  Layout Id
     * @param add           The new fragment to be added
     * @param isAddStack   Whether to fall back to the stack
     * @param enterAnim    Admission animation
     * @param exitAnim       Play animation
     * @param popEnterAnim Stack animation
     * @param popExitAnim  Stack animation
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           final boolean isAddStack,
                           @AnimRes final int enterAnim,
                           @AnimRes final int exitAnim,
                           @AnimRes final int popEnterAnim,
                           @AnimRes final int popExitAnim) {
        FragmentTransaction ft = fm.beginTransaction();
        putArgs(add, new Args(containerId, false, isAddStack));
        addAnim(ft, enterAnim, exitAnim, popEnterAnim, popExitAnim);
        operate(TYPE_ADD_FRAGMENT, fm, ft, null, add);
    }

    /**
     *Add fragment
     *
     * @param fm             the fragment manager
     * @param add             The new fragment to be added
     * @param containerId     Layout Id
     * @param sharedElements Share elements
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           @NonNull final View... sharedElements) {
        add(fm, add, containerId, false, sharedElements);
    }

    /**
     * Add fragment
     *
     * @param fm             the fragment manager
     * @param add             The new fragment to be added
     * @param containerId   Layout Id
     * @param isAddStack      Whether to fall back to the stack
     * @param sharedElements Share elements
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           final boolean isAddStack,
                           @NonNull final View... sharedElements) {
        FragmentTransaction ft = fm.beginTransaction();
        putArgs(add, new Args(containerId, false, isAddStack));
        addSharedElement(ft, sharedElements);
        operate(TYPE_ADD_FRAGMENT, fm, ft, null, add);
    }

    /**
     * Add fragment
     *
     * @param fm          the fragment manager
     * @param add         The new fragment to be added
     * @param containerId Layout Id
     * @param showIndex   The fragment index to be displayed
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final List<Fragment> add,
                           @IdRes final int containerId,
                           final int showIndex) {
        add(fm, add.toArray(new Fragment[add.size()]), containerId, showIndex);
    }

    /**
     *  Add fragment
     *
     * @param fm          the fragment manager
     * @param add         The new fragment to be added
     * @param containerId Layout Id
     * @param showIndex   The fragment index to be displayed
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment[] add,
                           @IdRes final int containerId,
                           final int showIndex) {
        for (int i = 0, len = add.length; i < len; ++i) {
            putArgs(add[i], new Args(containerId, showIndex != i, false));
        }
        operateNoAnim(fm, TYPE_ADD_FRAGMENT, null, add);
    }

    /**
     * Show fragment
     *
     * @param show The fragment to be displayed
     */
    public static void show(@NonNull final Fragment show) {
        putArgs(show, false);
        operateNoAnim(show.getFragmentManager(), TYPE_SHOW_FRAGMENT, null, show);
    }

    /**
     * Show fragment
     *
     * @param fm the fragment manager
     */
    public static void show(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        for (Fragment show : fragments) {
            putArgs(show, false);
        }
        operateNoAnim(fm, TYPE_SHOW_FRAGMENT, null, fragments.toArray(new Fragment[fragments.size()]));
    }

    /**
     * Hide the fragment
     *
     * @param hide The fragment to hide
     */
    public static void hide(@NonNull final Fragment hide) {
        putArgs(hide, true);
        operateNoAnim(hide.getFragmentManager(), TYPE_HIDE_FRAGMENT, null, hide);
    }

    /**
     *  Hide the fragment
     *
     * @param fm the fragment manager
     */
    public static void hide(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        for (Fragment hide : fragments) {
            putArgs(hide, true);
        }
        operateNoAnim(fm, TYPE_HIDE_FRAGMENT, null, fragments.toArray(new Fragment[fragments.size()]));
    }

    /**
     * Hides the fragment before it is displayed
     *
     * @param showIndex The fragment index to be displayed
     * @param fragments To hide the fragments
     */
    public static void showHide(final int showIndex, @NonNull final List<Fragment> fragments) {
        showHide(fragments.get(showIndex), fragments);
    }

    /**
     * Hides the fragment before it is displayed
     *
     * @param show The fragment to be displayed
     * @param hide The fragment to hide
     */
    public static void showHide(@NonNull final Fragment show, @NonNull final List<Fragment> hide) {
        for (Fragment fragment : hide) {
            putArgs(fragment, fragment != show);
        }
        operateNoAnim(show.getFragmentManager(), TYPE_SHOW_HIDE_FRAGMENT, show,
                hide.toArray(new Fragment[hide.size()]));
    }

    /**
     * Hides the fragment before it is displayed
     *
     * @param showIndex The fragment index to be displayed
     * @param fragments To hide the fragments
     */
    public static void showHide(final int showIndex, @NonNull final Fragment... fragments) {
        showHide(fragments[showIndex], fragments);
    }

    /**
     * Hides the fragment before it is displayed
     *
     * @param show The fragment to be displayed
     * @param hide The fragment to hide
     */
    public static void showHide(@NonNull final Fragment show, @NonNull final Fragment... hide) {
        for (Fragment fragment : hide) {
            putArgs(fragment, fragment != show);
        }
        operateNoAnim(show.getFragmentManager(), TYPE_SHOW_HIDE_FRAGMENT, show, hide);
    }

    /**
     * Hides the fragment before it is displayed
     *
     * @param show The fragment to be displayed
     * @param hide The fragment to hide
     */
    public static void showHide(@NonNull final Fragment show,
                                @NonNull final Fragment hide) {
        putArgs(show, false);
        putArgs(hide, true);
        operateNoAnim(show.getFragmentManager(), TYPE_SHOW_HIDE_FRAGMENT, show, hide);
    }

    /**
     * Replace the fragment
     *
     * @param srcFragment  Source fragment
     * @param destFragment Target fragment
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment) {
        replace(srcFragment, destFragment, false);
    }

    /**
     * Replace the fragment
     *
     * @param srcFragment  Source fragment
     * @param destFragment Target fragment
     * @param isAddStack   Whether to fall back to the stack
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               final boolean isAddStack) {
        Args args = getArgs(srcFragment);
        replace(srcFragment.getFragmentManager(), destFragment, args.id, isAddStack);
    }

    /**
     * Replace the fragment
     *
     * @param srcFragment   Source fragment
     * @param destFragment Target fragment
     * @param enterAnim    Admission animation
     * @param exitAnim     Play animation
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim) {
        replace(srcFragment, destFragment, false, enterAnim, exitAnim, 0, 0);
    }

    /**
     *  Replace the fragment
     *
     * @param srcFragment   Source fragment
     * @param destFragment Target fragment
     * @param isAddStack   Whether to fall back to the stack
     * @param enterAnim     Admission animation
     * @param exitAnim     Play animation
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               final boolean isAddStack,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim) {
        replace(srcFragment, destFragment, isAddStack, enterAnim, exitAnim, 0, 0);
    }

    /**
     *  Replace the fragment
     *
     * @param srcFragment  Source fragment
     * @param destFragment Target fragment
     * @param enterAnim     Admission animation
     * @param exitAnim     Play animation
     * @param popEnterAnim Stack animation
     * @param popExitAnim  Stack animation
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim,
                               @AnimRes final int popEnterAnim,
                               @AnimRes final int popExitAnim) {
        replace(srcFragment, destFragment, false, enterAnim, exitAnim, popEnterAnim, popExitAnim);
    }

    /**
     * Replace the fragment
     *
     * @param srcFragment  Source fragment
     * @param destFragment Target fragment
     * @param isAddStack   Whether to fall back to the stack
     * @param enterAnim    Admission animation
     * @param exitAnim    Play animation
     * @param popEnterAnim Stack animation
     * @param popExitAnim  Stack animation
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               final boolean isAddStack,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim,
                               @AnimRes final int popEnterAnim,
                               @AnimRes final int popExitAnim) {
        Args args = getArgs(srcFragment);
        replace(srcFragment.getFragmentManager(), destFragment, args.id, isAddStack,
                enterAnim, exitAnim, popEnterAnim, popExitAnim);
    }

    /**
     * Replace the fragment
     *
     * @param srcFragment    Source fragment
     * @param destFragment   Target fragment
     * @param sharedElements Share elements
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               final View... sharedElements) {
        replace(srcFragment, destFragment, false, sharedElements);
    }

    /**
     * Replace the fragment
     *
     * @param srcFragment    Source fragment
     * @param destFragment   Target fragment
     * @param isAddStack     Whether to fall back to the stack
     * @param sharedElements Share elements
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               final boolean isAddStack,
                               final View... sharedElements) {
        Args args = getArgs(srcFragment);
        replace(srcFragment.getFragmentManager(), destFragment, args.id, isAddStack, sharedElements);
    }

    /**
     * Replace the fragment
     *
     * @param fm          The Fragment Manager
     * @param containerId Layout ID
     * @param fragment    fragment
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId) {
        replace(fm, fragment, containerId, false);
    }

    /**
     * Replace the fragment
     *
     * @param fm           The Fragment Manager
     * @param containerId Layout ID
     * @param fragment    fragment
     * @param isAddStack  Whether to fall back to the stack
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               final boolean isAddStack) {
        FragmentTransaction ft = fm.beginTransaction();
        putArgs(fragment, new Args(containerId, false, isAddStack));
        operate(TYPE_REPLACE_FRAGMENT, fm, ft, null, fragment);
    }

    /**
     * Replace the fragment
     *
     * @param fm          Fragment Manager
     * @param containerId Layout ID
     * @param fragment    fragment
     * @param enterAnim   Admission animation
     * @param exitAnim    Play animation
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim) {
        replace(fm, fragment, containerId, false, enterAnim, exitAnim, 0, 0);
    }

    /**
     * Replace the fragment
     *
     * @param fm          Fragment Manager
     * @param containerId Layout ID
     * @param fragment    fragment
     * @param isAddStack  Whether to fall back to the stack
     * @param enterAnim   Admission animation
     * @param exitAnim    Play animation
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               final boolean isAddStack,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim) {
        replace(fm, fragment, containerId, isAddStack, enterAnim, exitAnim, 0, 0);
    }

    /**
     * Replace the fragment
     *
     * @param fm           Fragment Manager
     * @param containerId  Layout ID
     * @param fragment     fragment
     * @param enterAnim     Admission animation
     * @param exitAnim     Play animation
     * @param popEnterAnim Stack animation
     * @param popExitAnim  Stack animation
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim,
                               @AnimRes final int popEnterAnim,
                               @AnimRes final int popExitAnim) {
        replace(fm, fragment, containerId, false, enterAnim, exitAnim, popEnterAnim, popExitAnim);
    }

    /**
     *  Replace the fragment
     *
     * @param fm           Fragment Manager
     * @param containerId  Layout ID
     * @param fragment     fragment
     * @param isAddStack   Whether to fall back to the stack
     * @param enterAnim    Admission animation
     * @param exitAnim     Play animation
     * @param popEnterAnim Stack animation
     * @param popExitAnim  Stack animation
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               final boolean isAddStack,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim,
                               @AnimRes final int popEnterAnim,
                               @AnimRes final int popExitAnim) {
        FragmentTransaction ft = fm.beginTransaction();
        putArgs(fragment, new Args(containerId, false, isAddStack));
        addAnim(ft, enterAnim, exitAnim, popEnterAnim, popExitAnim);
        operate(TYPE_REPLACE_FRAGMENT, fm, ft, null, fragment);
    }

    /**
     *  Replace the fragment
     *
     * @param fm             Fragment Manager
     * @param containerId    Layout ID
     * @param fragment       fragment
     * @param sharedElements Share elements
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               final View... sharedElements) {
        replace(fm, fragment, containerId, false, sharedElements);
    }

    /**
     * Replace the fragment
     *
     * @param fm             Fragment Manager
     * @param containerId    Layout ID
     * @param fragment       fragment
     * @param isAddStack     Whether to fall back to the stack
     * @param sharedElements Share elements
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               final boolean isAddStack,
                               final View... sharedElements) {
        FragmentTransaction ft = fm.beginTransaction();
        putArgs(fragment, new Args(containerId, false, isAddStack));
        addSharedElement(ft, sharedElements);
        operate(TYPE_REPLACE_FRAGMENT, fm, ft, null, fragment);
    }

    /**
     * Stack fragment
     *
     * @param fm the fragment manager
     */
    public static void pop(@NonNull final FragmentManager fm) {
        pop(fm, true);
    }

    /**
     * Stack fragment
     *
     * @param fm the fragment manager
     */
    public static void pop(@NonNull final FragmentManager fm,
                           final boolean isImmediate) {
        if (isImmediate) {
            fm.popBackStackImmediate();
        } else {
            fm.popBackStack();
        }
    }

    /**
     * Stack to the specified fragment
     *
     * @param fm          the fragment manager
     * @param popClz      The type of the stack fragment
     * @param isInclusive Whether the stack popClz fragment
     */
    public static void popTo(@NonNull final FragmentManager fm,
                             final Class<? extends Fragment> popClz,
                             final boolean isInclusive) {
        popTo(fm, popClz, isInclusive, true);
    }

    /**
     * Stack to the specified fragment
     *
     * @param fm          the fragment manager
     * @param popClz       The type of the stack fragment
     * @param isInclusive Whether the stack popClz fragment
     * @param isImmediate Whether the stack immediately
     */
    public static void popTo(@NonNull final FragmentManager fm,
                             final Class<? extends Fragment> popClz,
                             final boolean isInclusive,
                             final boolean isImmediate) {
        if (isImmediate) {
            fm.popBackStackImmediate(popClz.getName(),
                    isInclusive ? FragmentManager.POP_BACK_STACK_INCLUSIVE : 0);
        } else {
            fm.popBackStack(popClz.getName(),
                    isInclusive ? FragmentManager.POP_BACK_STACK_INCLUSIVE : 0);
        }
    }

    /**
     * Stack all the fragments
     *
     * @param fm the fragment manager
     */
    public static void popAll(@NonNull final FragmentManager fm) {
        popAll(fm, true);
    }

    /**
     * Stack all the fragments
     *
     * @param fm the fragment manager
     */
    public static void popAll(@NonNull final FragmentManager fm, final boolean isImmediate) {
        while (fm.getBackStackEntryCount() > 0) {
            if (isImmediate) {
                fm.popBackStackImmediate();
            } else {
                fm.popBackStack();
            }
        }
    }

    /**
     * Remove the fragment
     *
     * @param remove The fragment to remove
     */
    public static void remove(@NonNull final Fragment remove) {
        operateNoAnim(remove.getFragmentManager(), TYPE_REMOVE_FRAGMENT, null, remove);
    }

    /**
     * Remove the specified fragment

     *
     * @param removeTo    The fragment to be removed
     * @param isInclusive Whether to remove removeTo
     */
    public static void removeTo(@NonNull final Fragment removeTo, final boolean isInclusive) {
        operateNoAnim(removeTo.getFragmentManager(), TYPE_REMOVE_TO_FRAGMENT,
                isInclusive ? removeTo : null, removeTo);
    }

    /**
     * Remove all fragments
     *
     * @param fm the fragment manager
     */
    public static void removeAll(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        operateNoAnim(fm, TYPE_REMOVE_FRAGMENT, null, fragments.toArray(new Fragment[fragments.size()]));
    }

    private static void putArgs(final Fragment fragment, final Args args) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
            fragment.setArguments(bundle);
        }
        bundle.putInt(ARGS_ID, args.id);
        bundle.putBoolean(ARGS_IS_HIDE, args.isHide);
        bundle.putBoolean(ARGS_IS_ADD_STACK, args.isAddStack);
    }

    private static void putArgs(final Fragment fragment, final boolean isHide) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
            fragment.setArguments(bundle);
        }
        bundle.putBoolean(ARGS_IS_HIDE, isHide);
    }

    private static Args getArgs(final Fragment fragment) {
        Bundle bundle = fragment.getArguments();
        return new Args(bundle.getInt(ARGS_ID, fragment.getId()),
                bundle.getBoolean(ARGS_IS_HIDE),
                bundle.getBoolean(ARGS_IS_ADD_STACK));
    }

    private static void operateNoAnim(final FragmentManager fm,
                                      final int type,
                                      final Fragment src,
                                      Fragment... dest) {
        FragmentTransaction ft = fm.beginTransaction();
        operate(type, fm, ft, src, dest);
    }

    private static void operate(final int type,
                                final FragmentManager fm,
                                final FragmentTransaction ft,
                                final Fragment src,
                                final Fragment... dest) {
        if (src != null && src.isRemoving()) {
            Log.e("FragmentUtils", src.getClass().getName() + " is isRemoving");
            return;
        }
        String name = dest.getClass().getName();
        Bundle args;
        switch (type) {
            case TYPE_ADD_FRAGMENT:
                for (Fragment fragment : dest) {
                    args = fragment.getArguments();
                    Fragment fragmentByTag = fm.findFragmentByTag(name);
                    if (fragmentByTag != null) {
                        if (fragmentByTag.isAdded()) {
                            ft.remove(fragmentByTag);
                        }
                    }
                    ft.add(args.getInt(ARGS_ID), fragment, name);
                    if (args.getBoolean(ARGS_IS_HIDE)) ft.hide(fragment);
                    if (args.getBoolean(ARGS_IS_ADD_STACK)) ft.addToBackStack(name);
                }
                break;
            case TYPE_HIDE_FRAGMENT:
                for (Fragment fragment : dest) {
                    ft.hide(fragment);
                }
                break;
            case TYPE_SHOW_FRAGMENT:
                for (Fragment fragment : dest) {
                    ft.show(fragment);
                }
                break;
            case TYPE_SHOW_HIDE_FRAGMENT:
                ft.show(src);
                for (Fragment fragment : dest) {
                    if (fragment != src) {
                        ft.hide(fragment);
                    }
                }
                break;
            case TYPE_REPLACE_FRAGMENT:
                args = dest[0].getArguments();
                ft.replace(args.getInt(ARGS_ID), dest[0], name);
                if (args.getBoolean(ARGS_IS_ADD_STACK)) ft.addToBackStack(name);
                break;
            case TYPE_REMOVE_FRAGMENT:
                for (Fragment fragment : dest) {
                    if (fragment != src) {
                        ft.remove(fragment);
                    }
                }
                break;
            case TYPE_REMOVE_TO_FRAGMENT:
                for (int i = dest.length - 1; i >= 0; --i) {
                    Fragment fragment = dest[i];
                    if (fragment == dest[0]) {
                        if (src != null) ft.remove(fragment);
                        break;
                    }
                    ft.remove(fragment);
                }
                break;
        }
        ft.commitAllowingStateLoss();
    }

    private static void addAnim(final FragmentTransaction ft,
                                final int enter,
                                final int exit,
                                final int popEnter,
                                final int popExit) {
        ft.setCustomAnimations(enter, exit, popEnter, popExit);
    }

    private static void addSharedElement(final FragmentTransaction ft,
                                         final View... views) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (View view : views) {
                ft.addSharedElement(view, view.getTransitionName());
            }
        }
    }

    /**
     * Get the top fragment
     *
     * @param fm the fragment manager
     * @return Finally joined the fragment
     */
    public static Fragment getTop(@NonNull final FragmentManager fm) {
        return getTopIsInStack(fm, false);
    }

    /**
     * Get the top of the stack fragment
     *
     * @param fm the fragment manager
     * @return Finally joined the fragment
     */
    public static Fragment getTopInStack(@NonNull final FragmentManager fm) {
        return getTopIsInStack(fm, true);
    }

    private static Fragment getTopIsInStack(@NonNull final FragmentManager fm,
                                            final boolean isInStack) {
        List<Fragment> fragments = getFragments(fm);
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                if (isInStack && fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                    return fragment;
                } else {
                    return fragment;
                }
            }
        }
        return null;
    }

    /**
     * Get the top visible fragment
     *
     * @param fm the fragment manager
     * @return Top visible fragment
     */
    public static Fragment getTopShow(@NonNull final FragmentManager fm) {
        return getTopShowIsInStack(fm, false);
    }

    /**
     * Get the top visible fragment on the stack
     *
     * @param fm the fragment manager
     * @return The top of the stack is visible
     */
    public static Fragment getTopShowInStack(@NonNull final FragmentManager fm) {
        return getTopShowIsInStack(fm, true);
    }

    private static Fragment getTopShowIsInStack(@NonNull final FragmentManager fm,
                                                final boolean isInStack) {
        List<Fragment> fragments = getFragments(fm);
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null
                    && fragment.isResumed()
                    && fragment.isVisible()
                    && fragment.getUserVisibleHint()) {
                if (isInStack && fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                    return fragment;
                }
            } else {
                return fragment;
            }
        }
        return null;
    }

    /**
     * Get the same level of the fragment
     *
     * @param fm the fragment manager
     * @return The fragment in the fragment manager
     */
    public static List<Fragment> getFragments(@NonNull final FragmentManager fm) {
        @SuppressWarnings("RestrictedApi")
        List<Fragment> fragments = fm.getFragments();
        if (fragments == null || fragments.isEmpty()) return Collections.emptyList();
        return fragments;
    }

    /**
     * Get the fragment in the same level stack
     *
     * @param fm the fragment manager
     * @return The fragment in the fragment manager stack
     */
    public static List<Fragment> getFragmentsInStack(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        List<Fragment> result = new ArrayList<>();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                result.add(fragment);
            }
        }
        return result;
    }

    /**
     * Get all the fragments
     *
     * @param fm the fragment manager
     * @return All fragments
     */
    public static List<FragmentNode> getAllFragments(@NonNull final FragmentManager fm) {
        return getAllFragments(fm, new ArrayList<FragmentNode>());
    }

    private static List<FragmentNode> getAllFragments(@NonNull final FragmentManager fm,
                                                      final List<FragmentNode> result) {
        List<Fragment> fragments = getFragments(fm);
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                result.add(new FragmentNode(fragment,
                        getAllFragments(fragment.getChildFragmentManager(),
                                new ArrayList<FragmentNode>())));
            }
        }
        return result;
    }

    /**
     * Gets all the fragments in the stack
     *
     * @param fm the fragment manager
     * @return All fragments
     */
    public static List<FragmentNode> getAllFragmentsInStack(@NonNull final FragmentManager fm) {
        return getAllFragmentsInStack(fm, new ArrayList<FragmentNode>());
    }

    private static List<FragmentNode> getAllFragmentsInStack(@NonNull final FragmentManager fm,
                                                             final List<FragmentNode> result) {
        List<Fragment> fragments = getFragments(fm);
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null && fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                result.add(new FragmentNode(fragment,
                        getAllFragmentsInStack(fragment.getChildFragmentManager(),
                                new ArrayList<FragmentNode>())));
            }
        }
        return result;
    }

    /**
     * Find the fragment
     *
     * @param fm      the fragment manager
     * @param findClz The type of fragment to find
     * @return Find the fragment
     */
    public static Fragment findFragment(@NonNull final FragmentManager fm,
                                        final Class<? extends Fragment> findClz) {
        return fm.findFragmentByTag(findClz.getName());
    }

    /**
     * Handle the fragment back key
     * <p>If the fragment implements the OnBackClickListener interface, returns {@code true}: indicates that the consumer has returned the key event, and vice versa</p>
     * <p>See FragmentActivity for specific examples</p>
     *
     * @param fragment fragment
     * @return Whether to consume back events
     */
    public static boolean dispatchBackPress(@NonNull final Fragment fragment) {
        return fragment.isResumed()
                && fragment.isVisible()
                && fragment.getUserVisibleHint()
                && fragment instanceof OnBackClickListener
                && ((OnBackClickListener) fragment).onBackClick();
    }

    /**
     * Handle the fragment back key
     * <p>If the fragment implements the OnBackClickListener interface, returns {@code true}: indicates that the consumer has returned the key event, and vice versa</p>
     * <p>See FragmentActivity for specific examples</p>
     *
     * @param fm the fragment manager
     * @return Whether to consume back events
     * @return Whether to consume back events
     */
    public static boolean dispatchBackPress(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        if (fragments == null || fragments.isEmpty()) return false;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null
                    && fragment.isResumed()
                    && fragment.isVisible()
                    && fragment.getUserVisibleHint()
                    && fragment instanceof OnBackClickListener
                    && ((OnBackClickListener) fragment).onBackClick()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Set background color
     *
     * @param fragment fragment
     * @param color    Background color
     */
    public static void setBackgroundColor(@NonNull final Fragment fragment, @ColorInt final int color) {
        View view = fragment.getView();
        if (view != null) {
            view.setBackgroundColor(color);
        }
    }

    /**
     * Set background resources
     *
     * @param fragment fragment
     * @param resId    Resource Id
     */
    public static void setBackgroundResource(@NonNull final Fragment fragment, @DrawableRes final int resId) {
        View view = fragment.getView();
        if (view != null) {
            view.setBackgroundResource(resId);
        }
    }

    /**
     * Set the background
     *
     * @param fragment   fragment
     * @param background background
     */
    public static void setBackground(@NonNull final Fragment fragment, final Drawable background) {
        ViewCompat.setBackground(fragment.getView(), background);
    }

    private static class Args {
        int     id;
        boolean isHide;
        boolean isAddStack;

        private Args(final int id, final boolean isHide, final boolean isAddStack) {
            this.id = id;
            this.isHide = isHide;
            this.isAddStack = isAddStack;
        }
    }

    public static class FragmentNode {
        Fragment           fragment;
        List<FragmentNode> next;

        public FragmentNode(final Fragment fragment, final List<FragmentNode> next) {
            this.fragment = fragment;
            this.next = next;
        }

        @Override
        public String toString() {
            return fragment.getClass().getSimpleName()
                    + "->"
                    + ((next == null || next.isEmpty()) ? "no child" : next.toString());
        }
    }

    public interface OnBackClickListener {
        boolean onBackClick();
    }





}
