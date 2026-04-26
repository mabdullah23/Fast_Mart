package com.example.fastmart3.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fastmart3.R;
import com.example.fastmart3.models.Product;
import java.util.ArrayList;
import java.util.List;

public class SimpleProductAdapter extends RecyclerView.Adapter<SimpleProductAdapter.ViewHolder> {
    
    private List<Product> productList = new ArrayList<>();
    private OnProductInteractionListener listener;
    
    public interface OnProductInteractionListener {
        void onProductClick(Product product);
        void onCartClick(Product product);
    }
    
    public void setOnProductInteractionListener(OnProductInteractionListener listener) {
        this.listener = listener;
    }
    
    public void setProducts(List<Product> products) {
        this.productList = products;
        notifyDataSetChanged();
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
        
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onProductClick(product);
        });
        
        holder.ivCart.setOnClickListener(v -> {
            if (listener != null) listener.onCartClick(product);
        });
        
        // Hide favorite button for now
        holder.ivFavorite.setVisibility(View.GONE);
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