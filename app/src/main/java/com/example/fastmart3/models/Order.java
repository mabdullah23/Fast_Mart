package com.example.fastmart3.models;

import java.util.List;
import java.util.ArrayList;

public class Order {
    private String orderId, buyerId, buyerName, buyerPhone, buyerAddress, sellerId, sellerName, status;
    private List<OrderItem> items;
    private double totalAmount;
    private long timestamp;

    public Order() { items = new ArrayList<>(); }
    public Order(String buyerId, String buyerName, String buyerPhone, String buyerAddress,
                 String sellerId, String sellerName, List<OrderItem> items, double totalAmount) {
        this.buyerId = buyerId; this.buyerName = buyerName; this.buyerPhone = buyerPhone;
        this.buyerAddress = buyerAddress; this.sellerId = sellerId; this.sellerName = sellerName;
        this.items = items; this.totalAmount = totalAmount; this.status = "Pending";
        this.timestamp = System.currentTimeMillis();
    }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getBuyerId() { return buyerId; }
    public String getBuyerName() { return buyerName; }
    public String getBuyerPhone() { return buyerPhone; }
    public String getBuyerAddress() { return buyerAddress; }
    public String getSellerId() { return sellerId; }
    public String getSellerName() { return sellerName; }
    public List<OrderItem> getItems() { return items; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public long getTimestamp() { return timestamp; }
}
