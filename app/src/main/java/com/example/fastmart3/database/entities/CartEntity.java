package com.example.fastmart3.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

@Entity(tableName = "cart")
public class CartEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String productId;
    public String productName;
    public String productType;
    public double productPrice;
    public int quantity;
    public String sellerName;
    public String sellerId;
    public String imageBase64;
    public long addedTime;
    
    public CartEntity() {}
    
    @Ignore
    public CartEntity(String productId, String productName, String productType, 
                     double productPrice, int quantity, String sellerName, String sellerId, String imageBase64) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.sellerName = sellerName;
        this.sellerId = sellerId;
        this.imageBase64 = imageBase64;
        this.addedTime = System.currentTimeMillis();
    }
    
    public double getTotalPrice() {
        return productPrice * quantity;
    }
}