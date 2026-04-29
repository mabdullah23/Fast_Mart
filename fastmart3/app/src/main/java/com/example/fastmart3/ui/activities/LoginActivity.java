package com.example.fastmart3.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fastmart3.R;
import com.example.fastmart3.repositories.AuthRepository;
import com.example.fastmart3.utils.SharedPrefManager;
import com.example.fastmart3.utils.ValidationUtils;
import com.example.fastmart3.utils.Constants;

public class LoginActivity extends AppCompatActivity {
    
    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvSignup;
    private ProgressBar progressBar;
    private AuthRepository authRepository;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        // Initialize views
        etEmail = findViewById(R.id.et_login_email);
        etPassword = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.btn_login);
        tvSignup = findViewById(R.id.tv_signup_link);
        progressBar = findViewById(R.id.progress_bar);
        
        // Initialize repositories
        authRepository = new AuthRepository();
        sharedPrefManager = new SharedPrefManager(this);
        
        // Login button click
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        
        // SIGN UP link click - THIS IS THE IMPORTANT PART
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SignupActivity
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
    
    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email required");
            return;
        }
        if (!ValidationUtils.isValidEmail(email)) {
            etEmail.setError("Valid email required");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password required");
            return;
        }
        
        showLoading(true);
        
        authRepository.login(email, password, new AuthRepository.LoginCallback() {
            @Override
            public void onSuccess(String accountType, String userId, String userName) {
                showLoading(false);
                sharedPrefManager.saveUserInfo(userId, userName, email, accountType);
                Toast.makeText(LoginActivity.this, "Welcome " + userName + "!", Toast.LENGTH_SHORT).show();
                
                if (Constants.ACCOUNT_TYPE_SELLER.equals(accountType)) {
                    startActivity(new Intent(LoginActivity.this, SellerMainActivity.class));
                } else {
                    startActivity(new Intent(LoginActivity.this, BuyerMainActivity.class));
                }
                finish();
            }
            
            @Override
            public void onFailure(String errorMessage) {
                showLoading(false);
                Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
    
    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        btnLogin.setEnabled(!isLoading);
        btnLogin.setText(isLoading ? "" : "Login");
    }
}