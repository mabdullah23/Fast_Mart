package com.example.fastmart3.ui.fragments.seller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import com.example.fastmart3.utils.SharedPrefManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SellerOrderHistoryFragment extends Fragment {
    
    private RecyclerView recyclerView;
    private OrdersAdapter adapter;
    private List<OrderItem> orderList;
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
                        OrderItem order = orderSnap.getValue(OrderItem.class);
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
            }
        });
    }
    
    static class OrderItem {
        public String orderId, buyerId, buyerName, buyerPhone, buyerAddress;
        public double totalAmount;
        public long timestamp;
        public List<ProductItem> items;
        
        OrderItem() {}
    }
    
    static class ProductItem {
        public String productName;
        public int quantity;
        public double unitPrice;
        public double itemTotal;
    }
    
    static class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
        private List<OrderItem> orders;
        
        OrdersAdapter(List<OrderItem> orders) {
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
            OrderItem order = orders.get(position);
            holder.tvBuyerName.setText("Buyer: " + order.buyerName);
            holder.tvTotal.setText(String.format("Total: Rs. %.2f", order.totalAmount));
            holder.tvItems.setText("Items: " + (order.items != null ? order.items.size() : 0));
        }
        
        @Override
        public int getItemCount() { return orders.size(); }
        
        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvBuyerName, tvTotal, tvItems;
            ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvBuyerName = itemView.findViewById(R.id.tv_buyer_name);
                tvTotal = itemView.findViewById(R.id.tv_total);
                tvItems = itemView.findViewById(R.id.tv_items);
            }
        }
    }
}