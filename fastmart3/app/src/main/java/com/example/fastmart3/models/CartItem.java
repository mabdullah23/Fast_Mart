package com.example.fastmart3.models;

public class CartItem {
    private int id, quantity, imageDrawable;
    private String productId, productName, productType, sellerName, imageUrl;
    private double productPrice;

    public CartItem() {}
    public CartItem(String productId, String productName, String productType, double productPrice, int quantity, String sellerName) {
        this.productId = productId; this.productName = productName; this.productType = productType;
        this.productPrice = productPrice; this.quantity = quantity; this.sellerName = sellerName;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getProductType() { return productType; }
    public double getProductPrice() { return productPrice; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getSellerName() { return sellerName; }
    public String getImageUrl() { return imageUrl; }
    public int getImageDrawable() { return imageDrawable; }
    public double getTotalPrice() { return productPrice * quantity; }
}
