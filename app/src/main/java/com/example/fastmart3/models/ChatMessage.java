package com.example.fastmart3.models;

public class ChatMessage {
    private String messageId;
    private String senderId;
    private String senderName;
    private String receiverId;
    private String receiverName;
    private String messageText;
    private long timestamp;
    private String chatId;
    private String productName;
    private String productId;

    public ChatMessage() {}

    public ChatMessage(String senderId, String senderName, String receiverId, 
                       String receiverName, String messageText) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.messageText = messageText;
        this.timestamp = System.currentTimeMillis();
        this.chatId = getChatId(senderId, receiverId);
    }
    
    public ChatMessage(String senderId, String senderName, String receiverId, 
                       String receiverName, String messageText, String productName, String productId) {
        this(senderId, senderName, receiverId, receiverName, messageText);
        this.productName = productName;
        this.productId = productId;
    }

    public static String getChatId(String user1, String user2) {
        if (user1.compareTo(user2) < 0) {
            return user1 + "_" + user2;
        } else {
            return user2 + "_" + user1;
        }
    }

    // Getters and Setters
    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }
    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }
    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }
    public String getReceiverId() { return receiverId; }
    public void setReceiverId(String receiverId) { this.receiverId = receiverId; }
    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }
    public String getMessageText() { return messageText; }
    public void setMessageText(String messageText) { this.messageText = messageText; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    public String getChatId() { return chatId; }
    public void setChatId(String chatId) { this.chatId = chatId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
}