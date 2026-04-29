package com.example.fastmart3.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static final String PREF_NAME = "fastmart3_prefs";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_ACCOUNT_TYPE = "account_type";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_THEME_MODE = "theme_mode";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_ADDRESS = "address";

    public SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveUserInfo(String userId, String userName, String email, String accountType) {
        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_USER_NAME, userName);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_ACCOUNT_TYPE, accountType);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }
    
    public void saveUserDetails(String phone, String address) {
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_ADDRESS, address);
        editor.apply();
    }

    public String getUserId() { return sharedPreferences.getString(KEY_USER_ID, ""); }
    public String getUserName() { return sharedPreferences.getString(KEY_USER_NAME, ""); }
    public String getUserEmail() { return sharedPreferences.getString(KEY_USER_EMAIL, ""); }
    public String getAccountType() { return sharedPreferences.getString(KEY_ACCOUNT_TYPE, ""); }
    public boolean isLoggedIn() { return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false); }
    public String getPhone() { return sharedPreferences.getString(KEY_PHONE, "+923001234567"); }
    public String getAddress() { return sharedPreferences.getString(KEY_ADDRESS, "Default Address"); }
    
    public void setThemeMode(String theme) { editor.putString(KEY_THEME_MODE, theme); editor.apply(); }
    public String getThemeMode() { return sharedPreferences.getString(KEY_THEME_MODE, "light"); }
    
    public void clearAll() { editor.clear(); editor.apply(); }
    public void logout() { clearAll(); }
}