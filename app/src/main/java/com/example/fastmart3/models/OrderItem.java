package com.example.fastmart3.models;

<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
import java.util.HashMap;
import java.util.Map;

public class OrderItem {
    private String productName;
    private String productType;
    private int quantity;
    private double unitPrice;
    private double itemTotal;
    private String imageBase64;  // Add image for order history

    public OrderItem() {}

    public OrderItem(String productName, String productType, int quantity, double unitPrice) {
        this.productName = productName;
        this.productType = productType;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.itemTotal = unitPrice * quantity;
    }
    
    public OrderItem(String productName, String productType, int quantity, double unitPrice, String imageBase64) {
        this(productName, productType, quantity, unitPrice);
        this.imageBase64 = imageBase64;
    }

    // Getters and Setters
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getProductType() { return productType; }
    public void setProductType(String productType) { this.productType = productType; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    public double getItemTotal() { return itemTotal; }
    public void setItemTotal(double itemTotal) { this.itemTotal = itemTotal; }
    public String getImageBase64() { return imageBase64; }
    public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("productName", productName);
        map.put("productType", productType);
        map.put("quantity", quantity);
        map.put("unitPrice", unitPrice);
        map.put("itemTotal", itemTotal);
        map.put("imageBase64", imageBase64);
        return map;
    }
<<<<<<< HEAD
}
=======
}
=======
public class OrderItem {
    private String productName, productType;
    private int quantity;
    private double unitPrice, itemTotal;

    public OrderItem() {}
    public OrderItem(String productName, String productType, int quantity, double unitPrice) {
        this.productName = productName; this.productType = productType;
        this.quantity = quantity; this.unitPrice = unitPrice; this.itemTotal = unitPrice * quantity;
    }
    public String getProductName() { return productName; }
    public String getProductType() { return productType; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    public double getItemTotal() { return itemTotal; }
}
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
