package com.example.fastmart3.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fastmart3.R;
import com.example.fastmart3.database.AppDatabase;
import com.example.fastmart3.database.entities.CartEntity;
import com.example.fastmart3.database.entities.FavoritesEntity;
import com.example.fastmart3.models.Product;
import java.util.ArrayList;
import java.util.List;

public class EnhancedProductAdapter extends RecyclerView.Adapter<EnhancedProductAdapter.ViewHolder> {
    
    private List<Product> productList = new ArrayList<>();
    private OnProductInteractionListener listener;
    private AppDatabase db;
    private android.content.Context context;
    
    public interface OnProductInteractionListener {
        void onProductClick(Product product);
        void onFavoriteClick(Product product, boolean isFavorite);
        void onCartClick(Product product);
    }
    
    public void setOnProductInteractionListener(OnProductInteractionListener listener) {
        this.listener = listener;
    }
    
    public void setProducts(List<Product> products, android.content.Context context) {
        this.productList = products;
        this.context = context;
        this.db = AppDatabase.getInstance(context);
        notifyDataSetChanged();
    }
    
    private boolean isFavorite(String productId) {
        if (db == null) return false;
        return db.favoritesDao().isFavorite(productId) > 0;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_enhanced, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(String.format("Rs. %.2f", product.getPrice()));
        holder.tvType.setText(product.getType());
        
        boolean isFav = isFavorite(product.getProductId());
        holder.ivFavorite.setImageResource(isFav ? R.drawable.ic_favorite_filled : R.drawable.ic_favorite_border);
        
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onProductClick(product);
        });
        
        holder.ivFavorite.setOnClickListener(v -> {
            boolean newFavState = !isFavorite(product.getProductId());
            if (newFavState) {
                FavoritesEntity fav = new FavoritesEntity(product.getProductId(), product.getName(),
<<<<<<< HEAD
                        product.getType(), product.getPrice(), product.getSellerName(), "");
=======
<<<<<<< HEAD
                        product.getType(), product.getPrice(), product.getSellerName(), "");
=======
                        product.getType(), product.getPrice(), product.getSellerName());
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
                db.favoritesDao().insert(fav);
            } else {
                db.favoritesDao().deleteByProductId(product.getProductId());
            }
            holder.ivFavorite.setImageResource(newFavState ? R.drawable.ic_favorite_filled : R.drawable.ic_favorite_border);
            if (listener != null) listener.onFavoriteClick(product, newFavState);
        });
        
        holder.ivCart.setOnClickListener(v -> {
            CartEntity existing = db.cartDao().getCartItemByProductId(product.getProductId());
            if (existing != null) {
                existing.quantity++;
                db.cartDao().update(existing);
            } else {
                CartEntity cartItem = new CartEntity(product.getProductId(), product.getName(),
<<<<<<< HEAD
                        product.getType(), product.getPrice(), 1, product.getSellerName(), product.getSellerId(), "");
=======
<<<<<<< HEAD
                        product.getType(), product.getPrice(), 1, product.getSellerName(), product.getSellerId(), "");
=======
                        product.getType(), product.getPrice(), 1, product.getSellerName(), product.getSellerId());
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
                db.cartDao().insert(cartItem);
            }
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