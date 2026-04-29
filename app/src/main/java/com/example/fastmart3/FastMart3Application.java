package com.example.fastmart3;

import android.app.Application;
import com.example.fastmart3.utils.ThemeManager;
import com.google.firebase.FirebaseApp;

public class FastMart3Application extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        // Apply saved theme on app start
        ThemeManager.applyTheme(this);
    }
}