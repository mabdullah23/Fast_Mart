package com.example.fastmart3.ui.activities;

import android.app.AlertDialog;
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
import android.graphics.Bitmap;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
<<<<<<< HEAD
=======
=======
import android.os.Bundle;
import android.telephony.SmsManager;
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
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
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
import com.example.fastmart3.models.Order;
import com.example.fastmart3.models.OrderItem;
import com.example.fastmart3.utils.ImageUtility;
import com.example.fastmart3.utils.SharedPrefManager;
import java.util.ArrayList;
import java.util.List;
<<<<<<< HEAD
=======
=======
import com.example.fastmart3.utils.SharedPrefManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec

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
                
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
                // Prepare order summary
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
                StringBuilder smsBody = new StringBuilder("FastMart Order Summary:\n\n");
                double totalAmount = 0;
                String sellerId = cartItems.get(0).sellerId;
                String sellerName = cartItems.get(0).sellerName;
                
<<<<<<< HEAD
                List<OrderItem> orderItems = new ArrayList<>();
                
=======
<<<<<<< HEAD
                List<OrderItem> orderItems = new ArrayList<>();
                
=======
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
                for (CartEntity item : cartItems) {
                    double itemTotal = item.productPrice * item.quantity;
                    totalAmount += itemTotal;
                    smsBody.append(item.productName).append(" x").append(item.quantity)
                           .append(" = Rs.").append(itemTotal).append("\n");
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
                    
                    OrderItem orderItem = new OrderItem(item.productName, item.productType, 
                            item.quantity, item.productPrice, item.imageBase64);
                    orderItems.add(orderItem);
<<<<<<< HEAD
=======
=======
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
                }
                smsBody.append("\nTotal: Rs.").append(totalAmount);
                smsBody.append("\n\nThank you for shopping with FastMart!");
                
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
                // Send SMS
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
                try {
                    String phoneNumber = sp.getPhone();
                    SmsManager.getDefault().sendTextMessage(phoneNumber, null, smsBody.toString(), null, null);
                    Toast.makeText(CartActivity.this, "Order summary sent via SMS to " + phoneNumber, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(CartActivity.this, "SMS failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
                // Save order to Firebase for seller
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
                String buyerId = sp.getUserId();
                String buyerName = sp.getUserName();
                String buyerPhone = sp.getPhone();
                String buyerAddress = sp.getAddress();
                
<<<<<<< HEAD
                Order order = new Order(buyerId, buyerName, buyerPhone, buyerAddress,
                        sellerId, sellerName, orderItems, totalAmount);
                
=======
<<<<<<< HEAD
                Order order = new Order(buyerId, buyerName, buyerPhone, buyerAddress,
                        sellerId, sellerName, orderItems, totalAmount);
                
=======
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
                DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders")
                        .child(sellerId).child(buyerId);
                
                String orderId = ordersRef.push().getKey();
                if (orderId != null) {
<<<<<<< HEAD
                    ordersRef.child(orderId).setValue(order.toMap());
                }
                
=======
<<<<<<< HEAD
                    ordersRef.child(orderId).setValue(order.toMap());
                }
                
=======
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
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
                new AlertDialog.Builder(CartActivity.this)
                    .setTitle("Order Placed Successfully!")
                    .setMessage("Your order has been placed.\nTotal: Rs. " + String.format("%.2f", totalAmount) + 
                               "\n\nOrder details sent via SMS.")
                    .setPositiveButton("OK", (dialog, which) -> {
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
                        // Clear cart
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
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
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
            
            if (item.imageBase64 != null && !TextUtils.isEmpty(item.imageBase64)) {
                Bitmap bitmap = ImageUtility.base64ToBitmap(item.imageBase64);
                if (bitmap != null) {
                    holder.ivProductImage.setImageBitmap(bitmap);
                } else {
                    holder.ivProductImage.setImageResource(R.drawable.ic_product_default);
                }
            } else {
                holder.ivProductImage.setImageResource(R.drawable.ic_product_default);
            }
            
<<<<<<< HEAD
=======
=======
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
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
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
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
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
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
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
            ImageView ivProductImage, btnPlus, btnMinus, ivDelete;
            TextView tvName, tvPrice, tvQuantity, tvTotal;
            ViewHolder(View itemView) {
                super(itemView);
                ivProductImage = itemView.findViewById(R.id.iv_product_image);
<<<<<<< HEAD
=======
=======
            TextView tvName, tvPrice, tvQuantity, tvTotal;
            ImageView btnPlus, btnMinus, ivDelete;
            ViewHolder(View itemView) {
                super(itemView);
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
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