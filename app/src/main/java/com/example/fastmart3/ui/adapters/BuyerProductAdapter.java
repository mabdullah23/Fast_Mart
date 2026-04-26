package com.example.fastmart3.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fastmart3.R;
import com.example.fastmart3.database.DatabaseHelper;
import com.example.fastmart3.models.Product;
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
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buyer_product, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(String.format("Rs. %.2f", product.getPrice()));
        holder.tvType.setText(product.getType());
        
        // Set favorite icon
        if (isFavorite(product.getProductId())) {
            holder.ivFavorite.setImageResource(R.drawable.ic_favorite_filled);
        } else {
            holder.ivFavorite.setImageResource(R.drawable.ic_favorite_border);
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
    }
    
    @Override
    public int getItemCount() { return productList.size(); }
    
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvType;
        ImageView ivFavorite, ivCart;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_product_name);
            tvPrice = itemView.findViewById(R.id.tv_product_price);
            tvType = itemView.findViewById(R.id.tv_product_type);
            ivFavorite = itemView.findViewById(R.id.iv_favorite);
            ivCart = itemView.findViewById(R.id.iv_cart);
        }
    }
}