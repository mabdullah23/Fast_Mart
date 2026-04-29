package com.example.fastmart3.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fastmart3.R;
import com.example.fastmart3.utils.SharedPrefManager;
import com.example.fastmart3.utils.Constants;

public class MainActivity extends AppCompatActivity {
    
    private SharedPrefManager sharedPrefManager;
    private static final int SPLASH_DISPLAY_LENGTH = 2500; // 2.5 seconds
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        sharedPrefManager = new SharedPrefManager(this);
        
        // Show splash screen for 2.5 seconds
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            navigateToNextScreen();
        }, SPLASH_DISPLAY_LENGTH);
    }
    
    private void navigateToNextScreen() {
        if (sharedPrefManager.isLoggedIn()) {
            // User is logged in - go to appropriate home screen
            String accountType = sharedPrefManager.getAccountType();
            if (Constants.ACCOUNT_TYPE_SELLER.equals(accountType)) {
                startActivity(new Intent(MainActivity.this, SellerMainActivity.class));
            } else {
                startActivity(new Intent(MainActivity.this, BuyerMainActivity.class));
            }
        } else {
            // User not logged in - go to login screen
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        finish();
    }
}