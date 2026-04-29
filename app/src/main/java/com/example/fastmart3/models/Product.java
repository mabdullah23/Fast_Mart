package com.example.fastmart3.models;

import java.util.HashMap;
import java.util.Map;

public class Product {
    private String productId, sellerId, sellerName, name, type, description, imageBase64;
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
    public String getImageBase64() { return imageBase64; }
    public long getTimestamp() { return timestamp; }

    // Setters
    public void setProductId(String productId) { this.productId = productId; }
    public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; }
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setPrice(double price) { this.price = price; }
    public void setDescription(String description) { this.description = description; }
    public void setSellerId(String sellerId) { this.sellerId = sellerId; }
    public void setSellerName(String sellerName) { this.sellerName = sellerName; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("sellerId", sellerId);
        map.put("sellerName", sellerName);
        map.put("name", name);
        map.put("type", type);
        map.put("price", price);
        map.put("description", description);
        map.put("imageBase64", imageBase64);
        map.put("timestamp", timestamp);
        return map;
    }
}