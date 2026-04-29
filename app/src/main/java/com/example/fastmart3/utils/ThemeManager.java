package com.example.fastmart3.utils;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDelegate;

public class ThemeManager {
    private static final String PREF_NAME = "theme_prefs";
    private static final String KEY_THEME = "theme_mode";
    
    public static final int THEME_LIGHT = 0;
    public static final int THEME_DARK = 1;
<<<<<<< HEAD
    public static final int THEME_SYSTEM = 2;
=======
<<<<<<< HEAD
    public static final int THEME_SYSTEM = 2;
=======
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
    
    public static void applyTheme(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        int themeMode = prefs.getInt(KEY_THEME, THEME_LIGHT);
        
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
        switch (themeMode) {
            case THEME_DARK:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case THEME_SYSTEM:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
<<<<<<< HEAD
=======
=======
        if (themeMode == THEME_DARK) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
        }
    }
    
    public static void saveTheme(Context context, int themeMode) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(KEY_THEME, themeMode).apply();
        applyTheme(context);
    }
    
    public static int getThemeMode(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_THEME, THEME_LIGHT);
    }
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
    
    public static String getThemeName(Context context) {
        int mode = getThemeMode(context);
        switch (mode) {
            case THEME_DARK: return "Dark";
            case THEME_SYSTEM: return "System Default";
            default: return "Light";
        }
    }
<<<<<<< HEAD
=======
=======
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
}