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
    public String imageBase64;
    public long timestamp;
    
    public FavoritesEntity() {}
    
    @Ignore
    public FavoritesEntity(String productId, String productName, String productType, 
                          double productPrice, String sellerName, String imageBase64) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.productPrice = productPrice;
        this.sellerName = sellerName;
        this.imageBase64 = imageBase64;
        this.timestamp = System.currentTimeMillis();
    }
}