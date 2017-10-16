package com.example.sahmed.utilityapp.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sahmed on 10/10/2017.
 */

public class SharedUtils {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private String DefaultString = "";
    private int DefaultInteger = -1;
    private float DefaultFloat = -1.0f;
    private boolean DefaultBoolean = false;
    private String FileName = "UserInfo";

    public static SharedUtils instance = new SharedUtils();

    /**
     * SharedUtils default constructor called when create new object
     */
    public SharedUtils() {

    }

    /**
     * SharedUtils  constructor called when create new object with context
     * and  initialize  SharedPreferences
     *
     * @param context context
     */
    public SharedUtils(Context context) {
        prefs = context.getSharedPreferences(FileName, Context.MODE_PRIVATE);

    }

    /**
     * getPrefs  method called to get SharedPreferences value
     *
     */
    public SharedPreferences getPrefs() {
        return prefs;
    }

    /**
     * getInstance  method called to get instance of SharedUtils
     *
     */
    public static SharedUtils getInstance() {
        if (instance == null) {
            instance = new SharedUtils();
        }
        return instance;
    }


    /**
     * setString  method called to set string value in SharedPreferences
     *
     * @param key key of string you want to save it in SharedPreferences
     * @param value value of string will save  in SharedPreferences
     */
    public void setString(String key, String value) {
        editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }


    /**
     * getString  method called to get string value from SharedPreferences
     *
     * @param key key of string you want to get from SharedPreferences
     */
    public String getString(String key) {
        return prefs.getString(key, DefaultString);
    }


    /**
     * setInt  method called to set integer value in SharedPreferences
     *
     * @param key key of integer you want to save it in SharedPreferences
     * @param value value of integer will save  in SharedPreferences
     */
    public void setInt(String key, int value) {
        editor = prefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }


    /**
     * getInt  method called to get int value from SharedPreferences
     *
     * @param key key of int you want to get from SharedPreferences
     */
    public int getInt(String key) {
        return prefs.getInt(key, DefaultInteger);
    }


    /**
     * setFloat  method called to set float value in SharedPreferences
     *
     * @param key key of float you want to save it in SharedPreferences
     * @param value value of float will save  in SharedPreferences
     */
    public void setFloat(String key, float value) {
        editor = prefs.edit();
        editor.putFloat(key, value);
        editor.commit();
    }


    /**
     * getFloat  method called to get float value from SharedPreferences
     *
     * @param key key of float you want to get from SharedPreferences
     */
    public float getFloat(String key) {
        return prefs.getFloat(key, DefaultFloat);
    }


    /**
     * setBoolean  method called to set boolean value in SharedPreferences
     *
     * @param key key of boolean you want to save it in SharedPreferences
     * @param value value of boolean will save  in SharedPreferences
     */
    public void setBoolean(String key, boolean value) {
        editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }


    /**
     * getBoolean  method called to get boolean value from SharedPreferences
     *
     * @param key key of boolean you want to get from SharedPreferences
     */
    public boolean getBoolean(String key) {
        return prefs.getBoolean(key, DefaultBoolean);
    }



    /**
     * delete  method called to clear SharedPreferences
     */
    public void delete() {
        editor = prefs.edit();
        editor.clear();
        editor.commit();
    }


}
