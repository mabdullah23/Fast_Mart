package com.example.fastmart3.models;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Order {
    private String orderId;
    private String buyerId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String sellerId;
    private String sellerName;
    private List<OrderItem> items;
    private double totalAmount;
    private String status;
    private long timestamp;

    public Order() {
        items = new ArrayList<>();
    }

    public Order(String buyerId, String buyerName, String buyerPhone, String buyerAddress,
                 String sellerId, String sellerName, List<OrderItem> items, double totalAmount) {
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.buyerPhone = buyerPhone;
        this.buyerAddress = buyerAddress;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = "Pending";
        this.timestamp = System.currentTimeMillis();
    }

    // Getters and Setters
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getBuyerId() { return buyerId; }
    public void setBuyerId(String buyerId) { this.buyerId = buyerId; }
    public String getBuyerName() { return buyerName; }
    public void setBuyerName(String buyerName) { this.buyerName = buyerName; }
    public String getBuyerPhone() { return buyerPhone; }
    public void setBuyerPhone(String buyerPhone) { this.buyerPhone = buyerPhone; }
    public String getBuyerAddress() { return buyerAddress; }
    public void setBuyerAddress(String buyerAddress) { this.buyerAddress = buyerAddress; }
    public String getSellerId() { return sellerId; }
    public void setSellerId(String sellerId) { this.sellerId = sellerId; }
    public String getSellerName() { return sellerName; }
    public void setSellerName(String sellerName) { this.sellerName = sellerName; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("buyerId", buyerId);
        map.put("buyerName", buyerName);
        map.put("buyerPhone", buyerPhone);
        map.put("buyerAddress", buyerAddress);
        map.put("sellerId", sellerId);
        map.put("sellerName", sellerName);
        map.put("totalAmount", totalAmount);
        map.put("status", status);
        map.put("timestamp", timestamp);
        
        // Add items
        List<Map<String, Object>> itemsList = new ArrayList<>();
        for (OrderItem item : items) {
            itemsList.add(item.toMap());
        }
        map.put("items", itemsList);
        
        return map;
    }
}