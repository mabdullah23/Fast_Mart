package com.example.fastmart3.ui.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fastmart3.R;
import com.example.fastmart3.models.Product;
import com.example.fastmart3.repositories.ProductRepository;
import com.example.fastmart3.utils.SharedPrefManager;
import com.example.fastmart3.utils.Constants;

public class ProductAddActivity extends AppCompatActivity {
    
    private EditText etName, etPrice, etDescription, etType;
    private Button btnSubmit;
    private ProgressBar progressBar;
    private ProductRepository productRepository;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);
        
        etName = findViewById(R.id.et_product_name);
        etPrice = findViewById(R.id.et_product_price);
        etDescription = findViewById(R.id.et_product_description);
        etType = findViewById(R.id.et_product_type);
        btnSubmit = findViewById(R.id.btn_submit_product);
        progressBar = findViewById(R.id.progress_bar);
        
        productRepository = new ProductRepository();
        sharedPrefManager = new SharedPrefManager(this);
        
        btnSubmit.setOnClickListener(v -> addProduct());
    }
    
    private void addProduct() {
        String name = etName.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String type = etType.getText().toString().trim();
        
        if (TextUtils.isEmpty(name)) {
            etName.setError("Product name required");
            return;
        }
        if (TextUtils.isEmpty(priceStr)) {
            etPrice.setError("Price required");
            return;
        }
        
        double price = Double.parseDouble(priceStr);
        String sellerId = sharedPrefManager.getUserId();
        String sellerName = sharedPrefManager.getUserName();
        
        showLoading(true);
        
        Product product = new Product(name, type, price, description, sellerId, sellerName);
        
        productRepository.addProduct(sellerId, product, new ProductRepository.AddProductCallback() {
            @Override
            public void onSuccess() {
                showLoading(false);
                Toast.makeText(ProductAddActivity.this, "Product added successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }
            @Override
            public void onFailure(String errorMessage) {
                showLoading(false);
                Toast.makeText(ProductAddActivity.this, "Failed: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? android.view.View.VISIBLE : android.view.View.GONE);
        btnSubmit.setEnabled(!isLoading);
        btnSubmit.setText(isLoading ? "" : "Add Product");
    }
}