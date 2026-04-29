package com.example.fastmart3.ui.fragments.seller;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.fastmart3.R;
import com.example.fastmart3.models.Order;
import com.example.fastmart3.models.OrderItem;
import com.example.fastmart3.utils.ImageUtility;
import com.example.fastmart3.utils.SharedPrefManager;
import java.util.ArrayList;
import java.util.List;

public class SellerOrderHistoryFragment extends Fragment {
    
    private RecyclerView recyclerView;
    private OrdersAdapter adapter;
    private List<Order> orderList;
    private SharedPrefManager sharedPrefManager;
    private TextView tvEmpty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_orders, container, false);
        
        recyclerView = view.findViewById(R.id.recycler_orders);
        tvEmpty = view.findViewById(R.id.tv_empty);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        orderList = new ArrayList<>();
        adapter = new OrdersAdapter(orderList);
        recyclerView.setAdapter(adapter);
        
        sharedPrefManager = new SharedPrefManager(getContext());
        
        loadOrders();
        
        return view;
    }
    
    private void loadOrders() {
        String sellerId = sharedPrefManager.getUserId();
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders").child(sellerId);
        
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderList.clear();
                for (DataSnapshot buyerSnap : snapshot.getChildren()) {
                    for (DataSnapshot orderSnap : buyerSnap.getChildren()) {
                        Order order = orderSnap.getValue(Order.class);
                        if (order != null) {
                            orderList.add(order);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
                tvEmpty.setVisibility(orderList.isEmpty() ? View.VISIBLE : View.GONE);
            }
            
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                tvEmpty.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Failed to load orders", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    static class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
        private List<Order> orders;
        
        OrdersAdapter(List<Order> orders) {
            this.orders = orders;
        }
        
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
            return new ViewHolder(view);
        }
        
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Order order = orders.get(position);
            holder.tvBuyerName.setText("Buyer: " + order.getBuyerName());
            holder.tvTotal.setText(String.format("Total: Rs. %.2f", order.getTotalAmount()));
            holder.tvItems.setText("Items: " + (order.getItems() != null ? order.getItems().size() : 0));
            holder.tvStatus.setText("Status: " + order.getStatus());
            
            // Show first item image if available
            if (order.getItems() != null && !order.getItems().isEmpty()) {
                OrderItem firstItem = order.getItems().get(0);
                if (firstItem.getImageBase64() != null && !TextUtils.isEmpty(firstItem.getImageBase64())) {
                    Bitmap bitmap = ImageUtility.base64ToBitmap(firstItem.getImageBase64());
                    if (bitmap != null) {
                        holder.ivProductImage.setImageBitmap(bitmap);
                    } else {
                        holder.ivProductImage.setImageResource(R.drawable.ic_product_default);
                    }
                } else {
                    holder.ivProductImage.setImageResource(R.drawable.ic_product_default);
                }
                
                // Show first product name
                if (order.getItems().size() == 1) {
                    holder.tvProductName.setText(firstItem.getProductName());
                } else {
                    holder.tvProductName.setText(firstItem.getProductName() + " + " + (order.getItems().size() - 1) + " more");
                }
            } else {
                holder.ivProductImage.setImageResource(R.drawable.ic_product_default);
                holder.tvProductName.setText("No items");
            }
        }
        
        @Override
        public int getItemCount() { return orders.size(); }
        
        static class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivProductImage;
            TextView tvBuyerName, tvProductName, tvTotal, tvItems, tvStatus;
            ViewHolder(@NonNull View itemView) {
                super(itemView);
                ivProductImage = itemView.findViewById(R.id.iv_product_image);
                tvBuyerName = itemView.findViewById(R.id.tv_buyer_name);
                tvProductName = itemView.findViewById(R.id.tv_product_name);
                tvTotal = itemView.findViewById(R.id.tv_total);
                tvItems = itemView.findViewById(R.id.tv_items);
                tvStatus = itemView.findViewById(R.id.tv_status);
            }
        }
    }
}