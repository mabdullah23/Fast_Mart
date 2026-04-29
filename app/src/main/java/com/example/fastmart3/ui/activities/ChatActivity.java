package com.example.fastmart3.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.firebase.database.*;
import com.example.fastmart3.R;
import com.example.fastmart3.models.ChatMessage;
import com.example.fastmart3.utils.SharedPrefManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {
    
    private Toolbar toolbar;
    private LinearLayout chatContainer;
    private EditText etMessage;
    private Button btnSend;
    private ScrollView scrollView;
    private DatabaseReference chatRef;
    private ValueEventListener chatListener;
    private SharedPrefManager sharedPrefManager;
    private String receiverId, receiverName, productName, productId;
    private Handler handler = new Handler();
    private static final long AUTO_SCROLL_DELAY = 100;
    private TextView tvProductContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        
        toolbar = findViewById(R.id.toolbar);
        chatContainer = findViewById(R.id.chat_container);
        etMessage = findViewById(R.id.et_message);
        btnSend = findViewById(R.id.btn_send);
        scrollView = findViewById(R.id.scroll_view);
        tvProductContext = findViewById(R.id.tv_product_context);
        
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        sharedPrefManager = new SharedPrefManager(this);
        
        // Get receiver info from intent
        receiverId = getIntent().getStringExtra("receiver_id");
        receiverName = getIntent().getStringExtra("receiver_name");
        productName = getIntent().getStringExtra("product_name");
        productId = getIntent().getStringExtra("product_id");
        
        String senderId = sharedPrefManager.getUserId();
        String senderName = sharedPrefManager.getUserName();
        String chatId = ChatMessage.getChatId(senderId, receiverId);
        
        // Set toolbar title
        if (productName != null && !productName.isEmpty()) {
            toolbar.setTitle("Chat about: " + productName);
            if (tvProductContext != null) {
                tvProductContext.setVisibility(View.VISIBLE);
                tvProductContext.setText("You are chatting with " + receiverName + " about \"" + productName + "\"");
            }
        } else {
            toolbar.setTitle("Chat with " + receiverName);
            if (tvProductContext != null) {
                tvProductContext.setVisibility(View.GONE);
            }
        }
        
        chatRef = FirebaseDatabase.getInstance().getReference("chats").child(chatId).child("messages");
        
        loadChatHistory();
        
        btnSend.setOnClickListener(v -> sendMessage(senderId, senderName));
    }
    
    private void loadChatHistory() {
        chatListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (chatContainer != null) {
                    chatContainer.removeAllViews();
                    for (DataSnapshot msgSnap : snapshot.getChildren()) {
                        ChatMessage message = msgSnap.getValue(ChatMessage.class);
                        if (message != null) {
                            addMessageToChat(message);
                        }
                    }
                    scrollToBottom();
                }
            }
            
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatActivity.this, "Failed to load messages", Toast.LENGTH_SHORT).show();
            }
        };
        chatRef.addValueEventListener(chatListener);
    }
    
    private void sendMessage(String senderId, String senderName) {
        String messageText = etMessage.getText().toString().trim();
        if (TextUtils.isEmpty(messageText)) {
            return;
        }
        
        ChatMessage message;
        if (productName != null && !productName.isEmpty()) {
            message = new ChatMessage(senderId, senderName, receiverId, receiverName, messageText, productName, productId);
        } else {
            message = new ChatMessage(senderId, senderName, receiverId, receiverName, messageText);
        }
        
        String messageId = chatRef.push().getKey();
        message.setMessageId(messageId);
        
        chatRef.child(messageId).setValue(message).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                etMessage.setText("");
                scrollToBottom();
            } else {
                Toast.makeText(ChatActivity.this, "Failed to send message", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void addMessageToChat(ChatMessage message) {
        if (chatContainer == null) return;
        
        String currentUserId = sharedPrefManager.getUserId();
        boolean isSentByMe = message.getSenderId().equals(currentUserId);
        
        View messageView = LayoutInflater.from(this).inflate(
            isSentByMe ? R.layout.item_chat_sent : R.layout.item_chat_received, 
            chatContainer, false);
        
        TextView tvMessage = messageView.findViewById(R.id.tv_message);
        TextView tvTimestamp = messageView.findViewById(R.id.tv_timestamp);
        TextView tvProductRef = messageView.findViewById(R.id.tv_product_ref);
        
        if (tvMessage != null) {
            tvMessage.setText(message.getMessageText());
        }
        
        // Show product reference if available and not sent by me
        if (tvProductRef != null) {
            if (!isSentByMe && message.getProductName() != null && !message.getProductName().isEmpty()) {
                tvProductRef.setVisibility(View.VISIBLE);
                tvProductRef.setText("Regarding: " + message.getProductName());
            } else {
                tvProductRef.setVisibility(View.GONE);
            }
        }
        
        if (tvTimestamp != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            tvTimestamp.setText(sdf.format(new Date(message.getTimestamp())));
        }
        
        chatContainer.addView(messageView);
    }
    
    private void scrollToBottom() {
        handler.postDelayed(() -> {
            if (scrollView != null) {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, AUTO_SCROLL_DELAY);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (chatListener != null && chatRef != null) {
            chatRef.removeEventListener(chatListener);
        }
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
