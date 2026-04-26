package com.example.fastmart3.models;

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
