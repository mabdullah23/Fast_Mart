package com.example.fastmart3.models;

import java.util.HashMap;
import java.util.Map;

public class Product {
    private String productId, sellerId, sellerName, name, type, description, imageUrl;
    private double price;
    private long timestamp;

    public Product() {}

    public Product(String name, String type, double price, String description, 
                   String sellerId, String sellerName) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.description = description;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters
    public String getProductId() { return productId; }
    public String getSellerId() { return sellerId; }
    public String getSellerName() { return sellerName; }
    public String getName() { return name; }
    public String getType() { return type; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public long getTimestamp() { return timestamp; }

    // Setters
    public void setProductId(String productId) { this.productId = productId; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("sellerId", sellerId);
        map.put("sellerName", sellerName);
        map.put("name", name);
        map.put("type", type);
        map.put("price", price);
        map.put("description", description);
        map.put("imageUrl", imageUrl);
        map.put("timestamp", timestamp);
        return map;
    }
}