package com.example.fastmart3.ui.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.fastmart3.R;
import com.example.fastmart3.database.DatabaseHelper;
import com.example.fastmart3.database.entities.CartEntity;
import com.example.fastmart3.utils.SharedPrefManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    
    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private TextView tvTotalPrice, tvEmpty;
    private Button btnCheckout;
    private DatabaseHelper dbHelper;
    private SharedPrefManager sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        
        recyclerView = findViewById(R.id.recycler_cart);
        tvTotalPrice = findViewById(R.id.tv_total_price);
        tvEmpty = findViewById(R.id.tv_empty);
        btnCheckout = findViewById(R.id.btn_checkout);
        
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        dbHelper = DatabaseHelper.getInstance(this);
        sp = new SharedPrefManager(this);
        
        loadCart();
        
        btnCheckout.setOnClickListener(v -> checkout());
    }
    
    private void loadCart() {
        dbHelper.getAllCartItems(new DatabaseHelper.CartCallback() {
            @Override
            public void onSuccess(List<CartEntity> cartItems) {
                adapter = new CartAdapter(cartItems);
                recyclerView.setAdapter(adapter);
                tvEmpty.setVisibility(cartItems.isEmpty() ? View.VISIBLE : View.GONE);
                updateTotal();
            }
            @Override
            public void onError(String error) {
                tvEmpty.setVisibility(View.VISIBLE);
            }
        });
    }
    
    private void updateTotal() {
        dbHelper.getTotalCartPrice(new DatabaseHelper.CartTotalCallback() {
            @Override
            public void onSuccess(double total) {
                tvTotalPrice.setText(String.format("Total: Rs. %.2f", total));
            }
        });
    }
    
    private void checkout() {
        dbHelper.getAllCartItems(new DatabaseHelper.CartCallback() {
            @Override
            public void onSuccess(List<CartEntity> cartItems) {
                if (cartItems.isEmpty()) {
                    Toast.makeText(CartActivity.this, "Cart is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                // Prepare order summary
                StringBuilder smsBody = new StringBuilder("FastMart Order Summary:\n\n");
                double totalAmount = 0;
                String sellerId = cartItems.get(0).sellerId;
                String sellerName = cartItems.get(0).sellerName;
                
                for (CartEntity item : cartItems) {
                    double itemTotal = item.productPrice * item.quantity;
                    totalAmount += itemTotal;
                    smsBody.append(item.productName).append(" x").append(item.quantity)
                           .append(" = Rs.").append(itemTotal).append("\n");
                }
                smsBody.append("\nTotal: Rs.").append(totalAmount);
                smsBody.append("\n\nThank you for shopping with FastMart!");
                
                // Send SMS
                try {
                    String phoneNumber = sp.getPhone();
                    SmsManager.getDefault().sendTextMessage(phoneNumber, null, smsBody.toString(), null, null);
                    Toast.makeText(CartActivity.this, "Order summary sent via SMS to " + phoneNumber, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(CartActivity.this, "SMS failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                
                // Save order to Firebase for seller
                String buyerId = sp.getUserId();
                String buyerName = sp.getUserName();
                String buyerPhone = sp.getPhone();
                String buyerAddress = sp.getAddress();
                
                DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders")
                        .child(sellerId).child(buyerId);
                
                String orderId = ordersRef.push().getKey();
                if (orderId != null) {
                    Map<String, Object> orderMap = new HashMap<>();
                    orderMap.put("orderId", orderId);
                    orderMap.put("buyerId", buyerId);
                    orderMap.put("buyerName", buyerName);
                    orderMap.put("buyerPhone", buyerPhone);
                    orderMap.put("buyerAddress", buyerAddress);
                    orderMap.put("sellerId", sellerId);
                    orderMap.put("sellerName", sellerName);
                    orderMap.put("totalAmount", totalAmount);
                    orderMap.put("timestamp", System.currentTimeMillis());
                    orderMap.put("status", "Pending");
                    
                    // Add items
                    for (int i = 0; i < cartItems.size(); i++) {
                        CartEntity item = cartItems.get(i);
                        Map<String, Object> itemMap = new HashMap<>();
                        itemMap.put("productName", item.productName);
                        itemMap.put("quantity", item.quantity);
                        itemMap.put("unitPrice", item.productPrice);
                        itemMap.put("itemTotal", item.productPrice * item.quantity);
                        orderMap.put("item_" + i, itemMap);
                    }
                    
                    ordersRef.child(orderId).setValue(orderMap);
                }
                
                // Show success dialog
                new AlertDialog.Builder(CartActivity.this)
                    .setTitle("Order Placed Successfully!")
                    .setMessage("Your order has been placed.\nTotal: Rs. " + String.format("%.2f", totalAmount) + 
                               "\n\nOrder details sent via SMS.")
                    .setPositiveButton("OK", (dialog, which) -> {
                        // Clear cart
                        dbHelper.clearCart();
                        loadCart();
                        updateTotal();
                    })
                    .show();
            }
            @Override
            public void onError(String error) {
                Toast.makeText(CartActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
        private List<CartEntity> cartItems;
        
        CartAdapter(List<CartEntity> items) {
            this.cartItems = items;
        }
        
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
            return new ViewHolder(view);
        }
        
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            CartEntity item = cartItems.get(position);
            holder.tvName.setText(item.productName);
            holder.tvPrice.setText(String.format("Rs. %.2f", item.productPrice));
            holder.tvQuantity.setText(String.valueOf(item.quantity));
            holder.tvTotal.setText(String.format("Rs. %.2f", item.getTotalPrice()));
            
            holder.btnPlus.setOnClickListener(v -> {
                item.quantity++;
                dbHelper.updateCartItem(item);
                holder.tvQuantity.setText(String.valueOf(item.quantity));
                holder.tvTotal.setText(String.format("Rs. %.2f", item.getTotalPrice()));
                updateTotal();
            });
            
            holder.btnMinus.setOnClickListener(v -> {
                if (item.quantity > 1) {
                    item.quantity--;
                    dbHelper.updateCartItem(item);
                    holder.tvQuantity.setText(String.valueOf(item.quantity));
                    holder.tvTotal.setText(String.format("Rs. %.2f", item.getTotalPrice()));
                    updateTotal();
                } else {
                    // If quantity is 1, delete the item
                    new AlertDialog.Builder(CartActivity.this)
                        .setTitle("Remove Item")
                        .setMessage("Do you want to remove this item from cart?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            dbHelper.deleteCartItem(item);
                            cartItems.remove(position);
                            notifyItemRemoved(position);
                            updateTotal();
                            if (cartItems.isEmpty()) tvEmpty.setVisibility(View.VISIBLE);
                        })
                        .setNegativeButton("No", null)
                        .show();
                }
            });
            
            holder.ivDelete.setOnClickListener(v -> {
                new AlertDialog.Builder(CartActivity.this)
                    .setTitle("Remove Item")
                    .setMessage("Do you want to remove this item from cart?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dbHelper.deleteCartItem(item);
                        cartItems.remove(position);
                        notifyItemRemoved(position);
                        updateTotal();
                        if (cartItems.isEmpty()) tvEmpty.setVisibility(View.VISIBLE);
                    })
                    .setNegativeButton("No", null)
                    .show();
            });
        }
        
        @Override
        public int getItemCount() { return cartItems.size(); }
        
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvName, tvPrice, tvQuantity, tvTotal;
            ImageView btnPlus, btnMinus, ivDelete;
            ViewHolder(View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tv_product_name);
                tvPrice = itemView.findViewById(R.id.tv_product_price);
                tvQuantity = itemView.findViewById(R.id.tv_quantity);
                tvTotal = itemView.findViewById(R.id.tv_total);
                btnPlus = itemView.findViewById(R.id.btn_plus);
                btnMinus = itemView.findViewById(R.id.btn_minus);
                ivDelete = itemView.findViewById(R.id.iv_delete);
            }
        }
    }
}