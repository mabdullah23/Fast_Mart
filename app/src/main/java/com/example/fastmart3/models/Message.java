package com.example.fastmart3.models;

import java.util.HashMap;
import java.util.Map;

public class Message {
    private String messageId, senderId, senderName, receiverId, receiverName, messageText;
    private long timestamp;

    public Message() {}
    public Message(String senderId, String senderName, String receiverId, String receiverName, String messageText) {
        this.senderId = senderId; this.senderName = senderName; this.receiverId = receiverId;
        this.receiverName = receiverName; this.messageText = messageText; this.timestamp = System.currentTimeMillis();
    }
    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }
    public String getSenderId() { return senderId; }
    public String getSenderName() { return senderName; }
    public String getReceiverId() { return receiverId; }
    public String getReceiverName() { return receiverName; }
    public String getMessageText() { return messageText; }
    public long getTimestamp() { return timestamp; }
}
