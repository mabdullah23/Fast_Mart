package com.example.fastmart3.repositories;

import androidx.annotation.NonNull;
import com.google.firebase.database.*;
import com.example.fastmart3.models.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private DatabaseReference productsRef;
    private ValueEventListener productListener;

    public ProductRepository() {
        productsRef = FirebaseDatabase.getInstance().getReference("products");
    }

    public interface ProductsCallback {
        void onSuccess(List<Product> products);
        void onFailure(String errorMessage);
    }

    public interface AddProductCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    public void addProduct(String sellerId, Product product, AddProductCallback callback) {
        String productId = productsRef.child(sellerId).push().getKey();
        if (productId != null) {
            product.setProductId(productId);
            productsRef.child(sellerId).child(productId).setValue(product.toMap())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) callback.onSuccess();
                    else callback.onFailure("Failed to add product");
                });
        } else {
            callback.onFailure("Failed to generate product ID");
        }
    }

    public void getAllProducts(ProductsCallback callback) {
        if (productListener != null) productsRef.removeEventListener(productListener);
        
        productListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Product> productList = new ArrayList<>();
                for (DataSnapshot sellerSnap : snapshot.getChildren()) {
                    for (DataSnapshot productSnap : sellerSnap.getChildren()) {
                        Product product = productSnap.getValue(Product.class);
                        if (product != null) productList.add(product);
                    }
                }
                callback.onSuccess(productList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(error.getMessage());
            }
        };
        productsRef.addValueEventListener(productListener);
    }

    public void getSellerProducts(String sellerId, ProductsCallback callback) {
        productsRef.child(sellerId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Product> productList = new ArrayList<>();
                for (DataSnapshot productSnap : snapshot.getChildren()) {
                    Product product = productSnap.getValue(Product.class);
                    if (product != null) productList.add(product);
                }
                callback.onSuccess(productList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(error.getMessage());
            }
        });
    }

    public void removeListener() {
        if (productListener != null) productsRef.removeEventListener(productListener);
    }
}