package com.example.fastmart3.ui.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fastmart3.R;
import com.example.fastmart3.database.DatabaseHelper;
import com.example.fastmart3.database.entities.CartEntity;

public class ProductDescriptionActivity extends AppCompatActivity {
    
    private TextView tvName, tvPrice, tvDescription, tvSeller;
    private Button btnBuyNow, btnAddToCart;
    private String productId, productName, productDescription, productSeller, productSellerId, productType;
    private double productPrice;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);
        
        tvName = findViewById(R.id.tv_product_name);
        tvPrice = findViewById(R.id.tv_product_price);
        tvDescription = findViewById(R.id.tv_product_description);
        tvSeller = findViewById(R.id.tv_product_seller);
        btnBuyNow = findViewById(R.id.btn_buy_now);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);
        
        dbHelper = DatabaseHelper.getInstance(this);
        
        // Get data from intent
        productId = getIntent().getStringExtra("product_id");
        productName = getIntent().getStringExtra("product_name");
        productPrice = getIntent().getDoubleExtra("product_price", 0);
        productDescription = getIntent().getStringExtra("product_description");
        productSeller = getIntent().getStringExtra("product_seller");
        productSellerId = getIntent().getStringExtra("product_seller_id");
        productType = getIntent().getStringExtra("product_type");
        
        // Set data to views
        tvName.setText(productName);
        tvPrice.setText(String.format("Price: Rs. %.2f", productPrice));
        tvDescription.setText(productDescription != null ? productDescription : "No description available");
        tvSeller.setText("Sold by: " + (productSeller != null ? productSeller : "Unknown Seller"));
        
        btnAddToCart.setOnClickListener(v -> addToCart());
        btnBuyNow.setOnClickListener(v -> buyNow());
    }
    
    private void addToCart() {
        CartEntity cartItem = new CartEntity(productId, productName, 
                productType != null ? productType : "General", 
                productPrice, 1, productSeller, productSellerId);
        dbHelper.insertCartItem(cartItem);
        Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
    }
    
    private void buyNow() {
        CartEntity cartItem = new CartEntity(productId, productName, 
                productType != null ? productType : "General", 
                productPrice, 1, productSeller, productSellerId);
        dbHelper.insertCartItem(cartItem);
        Toast.makeText(this, "Added to cart! Go to cart to checkout.", Toast.LENGTH_SHORT).show();
        finish();
    }
}