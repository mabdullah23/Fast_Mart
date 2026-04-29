package com.example.fastmart3.ui.activities;

<<<<<<< HEAD
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
=======
import android.os.Bundle;
import android.widget.Button;
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fastmart3.R;
import com.example.fastmart3.database.DatabaseHelper;
import com.example.fastmart3.database.entities.CartEntity;
<<<<<<< HEAD
import com.example.fastmart3.utils.ImageUtility;
=======
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec

public class ProductDescriptionActivity extends AppCompatActivity {
    
    private TextView tvName, tvPrice, tvDescription, tvSeller;
<<<<<<< HEAD
    private ImageView ivProductImage;
    private LinearLayout layoutButtons;
    private Button btnBuyNow, btnAddToCart;
    private String productId, productName, productDescription, productSeller, productSellerId, productType, imageBase64;
    private double productPrice;
    private boolean isSellerView;
=======
    private Button btnBuyNow, btnAddToCart;
    private String productId, productName, productDescription, productSeller, productSellerId, productType;
    private double productPrice;
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);
        
<<<<<<< HEAD
        ivProductImage = findViewById(R.id.iv_product_image);
=======
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
        tvName = findViewById(R.id.tv_product_name);
        tvPrice = findViewById(R.id.tv_product_price);
        tvDescription = findViewById(R.id.tv_product_description);
        tvSeller = findViewById(R.id.tv_product_seller);
<<<<<<< HEAD
        layoutButtons = findViewById(R.id.layout_buttons);
=======
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
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
<<<<<<< HEAD
        imageBase64 = getIntent().getStringExtra("product_image_base64");
        isSellerView = getIntent().getBooleanExtra("is_seller_view", false);
=======
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
        
        // Set data to views
        tvName.setText(productName);
        tvPrice.setText(String.format("Price: Rs. %.2f", productPrice));
<<<<<<< HEAD
        tvDescription.setText(productDescription != null && !productDescription.isEmpty() ? productDescription : "No description available");
        tvSeller.setText("Sold by: " + (productSeller != null ? productSeller : "Unknown Seller"));
        
        if (imageBase64 != null && !imageBase64.isEmpty()) {
            Bitmap bitmap = ImageUtility.base64ToBitmap(imageBase64);
            if (bitmap != null) {
                ivProductImage.setImageBitmap(bitmap);
            }
        }
        
        if (isSellerView) {
            layoutButtons.setVisibility(View.GONE);
        } else {
            layoutButtons.setVisibility(View.VISIBLE);
            btnAddToCart.setOnClickListener(v -> addToCart());
            btnBuyNow.setOnClickListener(v -> buyNow());
        }
=======
        tvDescription.setText(productDescription != null ? productDescription : "No description available");
        tvSeller.setText("Sold by: " + (productSeller != null ? productSeller : "Unknown Seller"));
        
        btnAddToCart.setOnClickListener(v -> addToCart());
        btnBuyNow.setOnClickListener(v -> buyNow());
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
    }
    
    private void addToCart() {
        CartEntity cartItem = new CartEntity(productId, productName, 
                productType != null ? productType : "General", 
<<<<<<< HEAD
                productPrice, 1, productSeller, productSellerId, imageBase64);
=======
<<<<<<< HEAD
                productPrice, 1, productSeller, productSellerId, "");
=======
                productPrice, 1, productSeller, productSellerId);
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
        dbHelper.insertCartItem(cartItem);
        Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
    }
    
    private void buyNow() {
        CartEntity cartItem = new CartEntity(productId, productName, 
                productType != null ? productType : "General", 
<<<<<<< HEAD
                productPrice, 1, productSeller, productSellerId, imageBase64);
=======
<<<<<<< HEAD
                productPrice, 1, productSeller, productSellerId, "");
=======
                productPrice, 1, productSeller, productSellerId);
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
        dbHelper.insertCartItem(cartItem);
        Toast.makeText(this, "Added to cart! Go to cart to checkout.", Toast.LENGTH_SHORT).show();
        finish();
    }
<<<<<<< HEAD
}
=======
<<<<<<< HEAD
}
=======
}
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
