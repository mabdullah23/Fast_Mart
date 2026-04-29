package com.example.fastmart3;

import android.app.Application;
<<<<<<< HEAD
import com.example.fastmart3.utils.ThemeManager;
import com.google.firebase.FirebaseApp;

public class FastMart3Application extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        // Apply saved theme on app start
        ThemeManager.applyTheme(this);
=======
import com.google.firebase.FirebaseApp;

public class FastMart3Application extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        // Initialize Firebase
        FirebaseApp.initializeApp(this);
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
    }
}