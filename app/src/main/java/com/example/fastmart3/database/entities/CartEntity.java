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
<<<<<<< HEAD
    public String imageBase64;
=======
<<<<<<< HEAD
    public String imageBase64;
=======
    public String imageUrl;
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
    public long addedTime;
    
    public CartEntity() {}
    
    @Ignore
    public CartEntity(String productId, String productName, String productType, 
<<<<<<< HEAD
                     double productPrice, int quantity, String sellerName, String sellerId, String imageBase64) {
=======
<<<<<<< HEAD
                     double productPrice, int quantity, String sellerName, String sellerId, String imageBase64) {
=======
                     double productPrice, int quantity, String sellerName, String sellerId) {
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.sellerName = sellerName;
        this.sellerId = sellerId;
<<<<<<< HEAD
        this.imageBase64 = imageBase64;
=======
<<<<<<< HEAD
        this.imageBase64 = imageBase64;
=======
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
        this.addedTime = System.currentTimeMillis();
    }
    
    public double getTotalPrice() {
        return productPrice * quantity;
    }
}