package com.example.fastmart3.ui.activities;

<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.fastmart3.R;
import com.example.fastmart3.models.Product;
import com.example.fastmart3.repositories.ProductRepository;
import com.example.fastmart3.utils.ImageUtility;
import com.example.fastmart3.utils.SharedPrefManager;
import java.io.IOException;

public class ProductAddActivity extends AppCompatActivity {
    
    private static final int PICK_IMAGE_REQUEST = 1;
    
    private EditText etName, etPrice, etDescription, etType;
    private Button btnSubmit, btnSelectImage;
    private ImageView ivProductImage;
    private ProgressBar progressBar;
    private ProductRepository productRepository;
    private SharedPrefManager sharedPrefManager;
    private Bitmap selectedBitmap;
    private String imageBase64;
    
<<<<<<< HEAD
=======
=======
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

>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);
        
        etName = findViewById(R.id.et_product_name);
        etPrice = findViewById(R.id.et_product_price);
        etDescription = findViewById(R.id.et_product_description);
        etType = findViewById(R.id.et_product_type);
        btnSubmit = findViewById(R.id.btn_submit_product);
<<<<<<< HEAD
        btnSelectImage = findViewById(R.id.btn_select_image);
        ivProductImage = findViewById(R.id.iv_product_image);
=======
<<<<<<< HEAD
        btnSelectImage = findViewById(R.id.btn_select_image);
        ivProductImage = findViewById(R.id.iv_product_image);
=======
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
        progressBar = findViewById(R.id.progress_bar);
        
        productRepository = new ProductRepository();
        sharedPrefManager = new SharedPrefManager(this);
        
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
        btnSelectImage.setOnClickListener(v -> openFileChooser());
        btnSubmit.setOnClickListener(v -> addProduct());
    }
    
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap originalBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                selectedBitmap = ImageUtility.resizeBitmap(originalBitmap, 300, 300);
                ivProductImage.setImageBitmap(selectedBitmap);
                imageBase64 = ImageUtility.bitmapToBase64(selectedBitmap);
                btnSelectImage.setText("Image Selected");
            } catch (IOException e) {
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
<<<<<<< HEAD
=======
=======
        btnSubmit.setOnClickListener(v -> addProduct());
    }
    
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
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
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
        if (imageBase64 == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }
<<<<<<< HEAD
=======
=======
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
        
        double price = Double.parseDouble(priceStr);
        String sellerId = sharedPrefManager.getUserId();
        String sellerName = sharedPrefManager.getUserName();
        
        showLoading(true);
        
        Product product = new Product(name, type, price, description, sellerId, sellerName);
<<<<<<< HEAD
        product.setImageBase64(imageBase64);
=======
<<<<<<< HEAD
        product.setImageBase64(imageBase64);
=======
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
        
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
<<<<<<< HEAD
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        btnSubmit.setEnabled(!isLoading);
        btnSelectImage.setEnabled(!isLoading);
=======
<<<<<<< HEAD
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        btnSubmit.setEnabled(!isLoading);
        btnSelectImage.setEnabled(!isLoading);
=======
        progressBar.setVisibility(isLoading ? android.view.View.VISIBLE : android.view.View.GONE);
        btnSubmit.setEnabled(!isLoading);
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
        btnSubmit.setText(isLoading ? "" : "Add Product");
    }
}