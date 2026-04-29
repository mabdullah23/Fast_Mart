package com.example.fastmart3;

import android.app.Application;
import com.google.firebase.FirebaseApp;

public class FastMart3Application extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        // Initialize Firebase
        FirebaseApp.initializeApp(this);
    }
}