package com.example.fastmart3.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

@Entity(tableName = "favorites")
public class FavoritesEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String productId;
    public String productName;
    public String productType;
    public double productPrice;
    public String sellerName;
<<<<<<< HEAD
    public String imageBase64;
=======
<<<<<<< HEAD
    public String imageBase64;
=======
    public String imageUrl;
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
    public long timestamp;
    
    public FavoritesEntity() {}
    
    @Ignore
    public FavoritesEntity(String productId, String productName, String productType, 
<<<<<<< HEAD
                          double productPrice, String sellerName, String imageBase64) {
=======
<<<<<<< HEAD
                          double productPrice, String sellerName, String imageBase64) {
=======
                          double productPrice, String sellerName) {
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.productPrice = productPrice;
        this.sellerName = sellerName;
<<<<<<< HEAD
        this.imageBase64 = imageBase64;
=======
<<<<<<< HEAD
        this.imageBase64 = imageBase64;
=======
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
        this.timestamp = System.currentTimeMillis();
    }
}