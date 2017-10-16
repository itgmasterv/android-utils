package com.example.sahmed.utilityapp.utility;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

/**
 * Created by sahmed on 10/9/2017.
 */

public class LanguageUtils {

    /**
     * setLanguage
     *
     * @param activity activity
     * @param position position of language you selected
     */
    public static void setLanguage(Activity activity, int position) {


        String language = "";
        if (position == 1) {
            language = "en";
        } else if (position == 2) {
            language = "ar";
        } else {
            language = "en";
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LanguageUtils.updateResources(activity, language);
        }

        LanguageUtils.updateResourcesLegacy(activity, language);

        // reload login page to set it by new language
        activity.recreate();

    }


    /**
     * updateResources
     * this function updateResources if the build version equal to or higher Nougat
     *
     * @param context  context
     * @param language language you want to update resources with it
     */

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);

        return context.createConfigurationContext(configuration);
    }


    /**
     * updateResourcesLegacy
     * this function updateResourcesLegacy if the build version not equal Nougat
     * and if build version equal to or higher Jelly Bean /marshmallow version
     *
     * @param context  context
     * @param language language you want to update resources with it
     */

    @SuppressWarnings("deprecation")
    private static Context updateResourcesLegacy(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale);
        }

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return context;
    }

}
