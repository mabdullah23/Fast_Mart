package com.example.fastmart3.ui.fragments.seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.*;
import com.example.fastmart3.R;
import com.example.fastmart3.models.ChatMessage;
import com.example.fastmart3.ui.activities.ChatActivity;
import com.example.fastmart3.utils.SharedPrefManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerChatListFragment extends Fragment {
    
    private RecyclerView recyclerView;
    private ChatListAdapter adapter;
    private List<ChatConversation> conversationList;
    private DatabaseReference chatsRef;
    private SharedPrefManager sharedPrefManager;
    private TextView tvEmpty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_chats, container, false);
        
        recyclerView = view.findViewById(R.id.recycler_chats);
        tvEmpty = view.findViewById(R.id.tv_empty);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        conversationList = new ArrayList<>();
        adapter = new ChatListAdapter(conversationList);
        recyclerView.setAdapter(adapter);
        
        sharedPrefManager = new SharedPrefManager(getContext());
        String sellerId = sharedPrefManager.getUserId();
        chatsRef = FirebaseDatabase.getInstance().getReference("chats");
        
        loadConversations(sellerId);
        
        return view;
    }
    
    private void loadConversations(String sellerId) {
        chatsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, ChatConversation> conversationMap = new HashMap<>();
                
                for (DataSnapshot chatSnap : snapshot.getChildren()) {
                    String chatId = chatSnap.getKey();
                    if (chatId != null && chatId.contains(sellerId)) {
                        // Get buyer ID
                        String[] parts = chatId.split("_");
                        String buyerId = parts[0].equals(sellerId) ? parts[1] : parts[0];
                        
                        // Get all messages for this chat
                        DataSnapshot messagesSnap = chatSnap.child("messages");
                        ChatConversation conv = conversationMap.get(buyerId);
                        if (conv == null) {
                            conv = new ChatConversation();
                            conv.buyerId = buyerId;
                            conv.chatId = chatId;
                        }
                        
                        for (DataSnapshot msgSnap : messagesSnap.getChildren()) {
                            ChatMessage msg = msgSnap.getValue(ChatMessage.class);
                            if (msg != null) {
                                // Update buyer name
                                if (msg.getSenderId().equals(buyerId)) {
                                    conv.buyerName = msg.getSenderName();
                                } else if (msg.getReceiverId().equals(buyerId)) {
                                    conv.buyerName = msg.getReceiverName();
                                }
                                
                                // Track last message
                                if (msg.getTimestamp() > conv.lastTimestamp) {
                                    conv.lastTimestamp = msg.getTimestamp();
                                    conv.lastMessage = msg.getMessageText();
                                    conv.lastMessageProduct = msg.getProductName();
                                }
                            }
                        }
                        conversationMap.put(buyerId, conv);
                    }
                }
                
                conversationList.clear();
                conversationList.addAll(conversationMap.values());
                conversationList.sort((a, b) -> Long.compare(b.lastTimestamp, a.lastTimestamp));
                adapter.notifyDataSetChanged();
                tvEmpty.setVisibility(conversationList.isEmpty() ? View.VISIBLE : View.GONE);
            }
            
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                tvEmpty.setVisibility(View.VISIBLE);
            }
        });
    }
    
    static class ChatConversation {
        String chatId;
        String buyerId;
        String buyerName;
        String lastMessage;
        String lastMessageProduct;
        long lastTimestamp;
    }
    
    static class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {
        private List<ChatConversation> conversations;
        
        ChatListAdapter(List<ChatConversation> conversations) {
            this.conversations = conversations;
        }
        
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_list, parent, false);
            return new ViewHolder(view);
        }
        
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ChatConversation conv = conversations.get(position);
            holder.tvBuyerName.setText(conv.buyerName != null ? conv.buyerName : "Buyer");
            
            String preview = conv.lastMessage;
            if (conv.lastMessageProduct != null && !conv.lastMessageProduct.isEmpty()) {
                preview = "[" + conv.lastMessageProduct + "] " + preview;
            }
            holder.tvLastMessage.setText(preview != null ? preview : "No messages yet");
            
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(holder.itemView.getContext(), ChatActivity.class);
                intent.putExtra("receiver_id", conv.buyerId);
                intent.putExtra("receiver_name", conv.buyerName != null ? conv.buyerName : "Buyer");
                holder.itemView.getContext().startActivity(intent);
            });
        }
        
        @Override
        public int getItemCount() { return conversations.size(); }
        
        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvBuyerName, tvLastMessage;
            ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvBuyerName = itemView.findViewById(R.id.tv_buyer_name);
                tvLastMessage = itemView.findViewById(R.id.tv_last_message);
            }
        }
    }
}