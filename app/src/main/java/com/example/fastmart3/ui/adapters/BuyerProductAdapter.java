package com.example.fastmart3.ui.adapters;

import android.content.Context;
<<<<<<< HEAD
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
=======
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
<<<<<<< HEAD
import androidx.core.content.ContextCompat;
=======
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
import androidx.recyclerview.widget.RecyclerView;
import com.example.fastmart3.R;
import com.example.fastmart3.database.DatabaseHelper;
import com.example.fastmart3.models.Product;
<<<<<<< HEAD
import com.example.fastmart3.ui.activities.ChatActivity;
import com.example.fastmart3.utils.ImageUtility;
import com.example.fastmart3.utils.ImageHelper;
=======
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyerProductAdapter extends RecyclerView.Adapter<BuyerProductAdapter.ViewHolder> {
    
    private List<Product> productList = new ArrayList<>();
    private OnProductInteractionListener listener;
    private Context context;
    private DatabaseHelper dbHelper;
    private Map<String, Boolean> favoriteStatus = new HashMap<>();
<<<<<<< HEAD
    private Map<String, Bitmap> imageCache = new HashMap<>();
=======
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
    
    public interface OnProductInteractionListener {
        void onProductClick(Product product);
        void onFavoriteClick(Product product);
        void onCartClick(Product product);
    }
    
    public void setOnProductInteractionListener(OnProductInteractionListener listener) {
        this.listener = listener;
    }
    
    public void setProducts(List<Product> products, Context context) {
        this.context = context;
        this.productList = products;
        this.dbHelper = DatabaseHelper.getInstance(context);
        loadFavoriteStatuses();
    }
    
    private void loadFavoriteStatuses() {
        for (Product product : productList) {
            dbHelper.isFavorite(product.getProductId(), new DatabaseHelper.FavoriteStatusCallback() {
                @Override
                public void onResult(boolean isFavorite) {
                    favoriteStatus.put(product.getProductId(), isFavorite);
                    notifyDataSetChanged();
                }
            });
        }
    }
    
    private boolean isFavorite(String productId) {
        return favoriteStatus.containsKey(productId) && favoriteStatus.get(productId);
    }
    
<<<<<<< HEAD
    private Bitmap getProductImage(Product product) {
        if (imageCache.containsKey(product.getProductId())) {
            return imageCache.get(product.getProductId());
        }
        
        Bitmap bitmap = null;
        if (product.getImageBase64() != null && !TextUtils.isEmpty(product.getImageBase64())) {
            bitmap = ImageUtility.base64ToBitmap(product.getImageBase64());
        }
        
        if (bitmap == null) {
            int defaultImage = ImageHelper.getImageForProductType(product.getType());
            bitmap = ImageUtility.drawableToBitmap(context, defaultImage);
        }
        
        imageCache.put(product.getProductId(), bitmap);
        return bitmap;
    }
    
=======
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buyer_product, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
<<<<<<< HEAD
        
        // Set product image from Base64
        Bitmap bitmap = getProductImage(product);
        if (bitmap != null) {
            holder.ivProductImage.setImageBitmap(bitmap);
        } else {
            holder.ivProductImage.setImageResource(R.drawable.ic_product_default);
        }
        
=======
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(String.format("Rs. %.2f", product.getPrice()));
        holder.tvType.setText(product.getType());
        
<<<<<<< HEAD
        // Set favorite icon and color
        if (isFavorite(product.getProductId())) {
            holder.ivFavorite.setImageResource(R.drawable.ic_favorite_filled);
            holder.ivFavorite.setColorFilter(Color.RED);
        } else {
            holder.ivFavorite.setImageResource(R.drawable.ic_favorite_border);
            // Using a very dark gray/black that adapts or stays dark as requested
            holder.ivFavorite.setColorFilter(Color.BLACK);
=======
        // Set favorite icon
        if (isFavorite(product.getProductId())) {
            holder.ivFavorite.setImageResource(R.drawable.ic_favorite_filled);
        } else {
            holder.ivFavorite.setImageResource(R.drawable.ic_favorite_border);
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
        }
        
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onProductClick(product);
        });
        
        holder.ivFavorite.setOnClickListener(v -> {
            if (listener != null) listener.onFavoriteClick(product);
        });
        
        holder.ivCart.setOnClickListener(v -> {
            if (listener != null) listener.onCartClick(product);
        });
<<<<<<< HEAD
        
        holder.ivChat.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("receiver_id", product.getSellerId());
            intent.putExtra("receiver_name", product.getSellerName());
            intent.putExtra("product_name", product.getName());
            intent.putExtra("product_id", product.getProductId());
            context.startActivity(intent);
        });
=======
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
    }
    
    @Override
    public int getItemCount() { return productList.size(); }
    
    static class ViewHolder extends RecyclerView.ViewHolder {
<<<<<<< HEAD
        ImageView ivProductImage, ivFavorite, ivCart, ivChat;
        TextView tvName, tvPrice, tvType;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.iv_product_image);
=======
        TextView tvName, tvPrice, tvType;
        ImageView ivFavorite, ivCart;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
            tvName = itemView.findViewById(R.id.tv_product_name);
            tvPrice = itemView.findViewById(R.id.tv_product_price);
            tvType = itemView.findViewById(R.id.tv_product_type);
            ivFavorite = itemView.findViewById(R.id.iv_favorite);
            ivCart = itemView.findViewById(R.id.iv_cart);
<<<<<<< HEAD
            ivChat = itemView.findViewById(R.id.iv_chat);
=======
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
        }
    }
}