package com.example.fastmart3.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fastmart3.R;
import com.example.fastmart3.models.User;
import com.example.fastmart3.repositories.AuthRepository;
import com.example.fastmart3.utils.ValidationUtils;
import com.example.fastmart3.utils.Constants;

public class SignupActivity extends AppCompatActivity {
    
    private EditText etName, etEmail, etPassword, etAddress, etPhone, etCountry, etDob;
    private RadioGroup rgGender;
    private Spinner spinnerAccountType;
    private Button btnSignup;
    private TextView tvLogin;
    private ProgressBar progressBar;
    private AuthRepository authRepository;
    private String selectedGender = "Male";
    private String selectedAccountType = "Buyer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        
        // Simple EditText - No TextInputEditText
        etName = findViewById(R.id.et_signup_name);
        etEmail = findViewById(R.id.et_signup_email);
        etPassword = findViewById(R.id.et_signup_password);
        etAddress = findViewById(R.id.et_signup_address);
        etPhone = findViewById(R.id.et_signup_phone);
        etCountry = findViewById(R.id.et_signup_country);
        etDob = findViewById(R.id.et_signup_dob);
        rgGender = findViewById(R.id.rg_gender);
        spinnerAccountType = findViewById(R.id.spinner_account_type);
        btnSignup = findViewById(R.id.btn_signup);
        tvLogin = findViewById(R.id.tv_login_link);
        progressBar = findViewById(R.id.progress_bar);
        
        authRepository = new AuthRepository();
        
        String[] accountTypes = {"Buyer", "Seller"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, accountTypes);
        spinnerAccountType.setAdapter(adapter);
        spinnerAccountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedAccountType = accountTypes[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        
        rgGender.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_male) selectedGender = "Male";
            else if (checkedId == R.id.rb_female) selectedGender = "Female";
        });
        
        btnSignup.setOnClickListener(v -> signupUser());
        tvLogin.setOnClickListener(v -> finish());
    }
    
    private void signupUser() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String country = etCountry.getText().toString().trim();
        String dob = etDob.getText().toString().trim();
        
        if (name.isEmpty()) {
            etName.setError("Name required");
            return;
        }
        if (!ValidationUtils.isValidEmail(email)) {
            etEmail.setError("Valid email required");
            return;
        }
        if (password.length() < 6) {
            etPassword.setError("Password must be 6+ characters");
            return;
        }
        if (address.isEmpty()) {
            etAddress.setError("Address required");
            return;
        }
        if (phone.isEmpty() || phone.length() < 10) {
            etPhone.setError("Valid phone number required");
            return;
        }
        if (country.isEmpty()) {
            etCountry.setError("Country required");
            return;
        }
        if (dob.isEmpty()) {
            etDob.setError("Date of Birth required");
            return;
        }
        
        showLoading(true);
        
        User user = new User(email, name, address, phone, country, dob, selectedGender, selectedAccountType);
        
        authRepository.signUp(user, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(String userId) {
                showLoading(false);
                Toast.makeText(SignupActivity.this, "Signup successful! Please login.", Toast.LENGTH_LONG).show();
                finish();
            }
            
            @Override
            public void onFailure(String errorMessage) {
                showLoading(false);
                Toast.makeText(SignupActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
    
    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        btnSignup.setEnabled(!isLoading);
        btnSignup.setText(isLoading ? "" : "Sign Up");
    }
}