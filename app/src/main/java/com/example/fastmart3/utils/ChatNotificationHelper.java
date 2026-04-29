package com.example.fastmart3.utils;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.firebase.database.*;

public class ChatNotificationHelper {
    
    private DatabaseReference chatsRef;
    private String currentUserId;
    private ValueEventListener chatListener;
    
    public interface NewMessageListener {
        void onNewMessage(String senderName, String message);
    }
    
    public ChatNotificationHelper(Context context, String userId) {
        this.currentUserId = userId;
        this.chatsRef = FirebaseDatabase.getInstance().getReference("chats");
    }
    
    public void listenForNewMessages(NewMessageListener listener) {
        chatListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot chatSnap : snapshot.getChildren()) {
                    String chatId = chatSnap.getKey();
                    if (chatId != null && chatId.contains(currentUserId)) {
                        DataSnapshot messagesSnap = chatSnap.child("messages");
                        for (DataSnapshot msgSnap : messagesSnap.getChildren()) {
                            String senderId = msgSnap.child("senderId").getValue(String.class);
                            if (senderId != null && !senderId.equals(currentUserId)) {
                                String messageText = msgSnap.child("messageText").getValue(String.class);
                                String senderName = msgSnap.child("senderName").getValue(String.class);
                                if (listener != null && messageText != null) {
                                    listener.onNewMessage(senderName, messageText);
                                }
                            }
                        }
                    }
                }
            }
            
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        };
        chatsRef.addValueEventListener(chatListener);
    }
    
    public void stopListening() {
        if (chatListener != null && chatsRef != null) {
            chatsRef.removeEventListener(chatListener);
        }
    }
}