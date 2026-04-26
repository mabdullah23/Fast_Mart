package com.example.fastmart3.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fastmart3.R;
import com.example.fastmart3.utils.SharedPrefManager;
import com.example.fastmart3.utils.Constants;

public class SplashActivity extends AppCompatActivity {
    
    private SharedPrefManager sharedPrefManager;
    private static final int SPLASH_DELAY = 2500;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        sharedPrefManager = new SharedPrefManager(this);
        
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (sharedPrefManager.isLoggedIn()) {
                String accountType = sharedPrefManager.getAccountType();
                if (Constants.ACCOUNT_TYPE_SELLER.equals(accountType)) {
                    startActivity(new Intent(SplashActivity.this, SellerMainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, BuyerMainActivity.class));
                }
            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
            finish();
        }, SPLASH_DELAY);
    }
}