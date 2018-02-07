package com.example.xiangxuchen.architecture_mvvm_opt.utils.common;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {

    private static Context sContext;

    private static String sPreferenceName = "sp";

    public static void init(Context context) {
        sContext = context.getApplicationContext();
    }

    public static void setsPreferenceName(String name) {
        sPreferenceName = name;
    }


    public static boolean putString(String key, String value) {
        SharedPreferences settings =
                sContext.getSharedPreferences(sPreferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }


    public static String getString(String key) {
        return getString(key, null);
    }


    public static String getString(String key, String defaultValue) {
        SharedPreferences settings =
                sContext.getSharedPreferences(sPreferenceName, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }

    public static void remove(String key) {
        SharedPreferences settings =
                sContext.getSharedPreferences(sPreferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        editor.commit();
    }


    public static void putInt(String key, int value) {
        SharedPreferences settings =
                sContext.getSharedPreferences(sPreferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
    }


    public static int getInt(String key) {
        return getInt(key, -1);
    }


    public static int getInt(String key, int defaultValue) {
        SharedPreferences settings =
                sContext.getSharedPreferences(sPreferenceName, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }


    public static void putLong(String key, long value) {
        SharedPreferences settings =
                sContext.getSharedPreferences(sPreferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        editor.commit();
    }


    public static long getLong(String key) {
        return getLong(key, -1);
    }


    public static long getLong(String key, long defaultValue) {
        SharedPreferences settings =
                sContext.getSharedPreferences(sPreferenceName, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }


    public static void putFloat(String key, float value) {
        SharedPreferences settings =
                sContext.getSharedPreferences(sPreferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        editor.commit();
    }


    public static float getFloat(String key) {
        return getFloat(key, -1);
    }


    public static float getFloat(String key, float defaultValue) {
        SharedPreferences settings =
                sContext.getSharedPreferences(sPreferenceName, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }


    public static void putBoolean(String key, boolean value) {
        SharedPreferences settings =
                sContext.getSharedPreferences(sPreferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }


    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }


    public static boolean getBoolean(String key, boolean defaultValue) {
        SharedPreferences settings =
                sContext.getSharedPreferences(sPreferenceName, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }


}

