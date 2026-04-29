package com.example.fastmart3.ui.fragments.buyer;

import android.app.AlertDialog;
<<<<<<< HEAD
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
=======
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;
import com.example.fastmart3.R;
import com.example.fastmart3.database.DatabaseHelper;
import com.example.fastmart3.database.entities.FavoritesEntity;
import com.example.fastmart3.database.entities.CartEntity;
<<<<<<< HEAD
import com.example.fastmart3.utils.ImageUtility;
=======
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
import java.util.List;

public class FavoritesFragment extends Fragment {
    
    private RecyclerView recyclerView;
    private FavoritesAdapter adapter;
    private DatabaseHelper dbHelper;
    private TextView tvEmpty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        
        recyclerView = view.findViewById(R.id.recycler_favorites);
        tvEmpty = view.findViewById(R.id.tv_empty);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        dbHelper = DatabaseHelper.getInstance(getContext());
        
        loadFavorites();
        
        return view;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        loadFavorites();
    }
    
    private void loadFavorites() {
        dbHelper.getAllFavorites(new DatabaseHelper.FavoritesCallback() {
            @Override
            public void onSuccess(List<FavoritesEntity> favorites) {
                adapter = new FavoritesAdapter(favorites);
                recyclerView.setAdapter(adapter);
                tvEmpty.setVisibility(favorites.isEmpty() ? View.VISIBLE : View.GONE);
            }
            @Override
            public void onError(String error) {
                tvEmpty.setVisibility(View.VISIBLE);
            }
        });
    }
    
    class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
        private List<FavoritesEntity> favorites;
        
        FavoritesAdapter(List<FavoritesEntity> favorites) {
            this.favorites = favorites;
        }
        
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
            return new ViewHolder(view);
        }
        
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            FavoritesEntity item = favorites.get(position);
<<<<<<< HEAD
            
            // Load image from Base64
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
            
=======
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
            holder.tvName.setText(item.productName);
            holder.tvPrice.setText(String.format("Rs. %.2f", item.productPrice));
            holder.tvType.setText(item.productType);
            
            holder.ivDelete.setOnClickListener(v -> {
                new AlertDialog.Builder(getContext())
                    .setTitle("Delete")
                    .setMessage("Do you want to delete this product from favourites?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dbHelper.deleteFavorite(item.productId);
                        favorites.remove(position);
                        notifyItemRemoved(position);
                        if (favorites.isEmpty()) tvEmpty.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
            });
            
<<<<<<< HEAD
            holder.ivCart.setOnClickListener(v -> {
                CartEntity cartItem = new CartEntity(item.productId, item.productName,
                        item.productType, item.productPrice, 1, item.sellerName, "", item.imageBase64);
                dbHelper.insertCartItem(cartItem);
                Toast.makeText(getContext(), item.productName + " added to cart", Toast.LENGTH_SHORT).show();
=======
            // Add to Cart from Favorites
            holder.ivCart.setOnClickListener(v -> {
                // Check if already in cart
                dbHelper.getCartItem(item.productId, cartItem -> {
                    if (cartItem != null) {
                        // Update quantity
                        cartItem.quantity++;
                        dbHelper.updateCartItem(cartItem);
                        Toast.makeText(getContext(), "Product already in cart! Quantity increased.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Add new item to cart
                        CartEntity newCartItem = new CartEntity(item.productId, item.productName,
                                item.productType, item.productPrice, 1, item.sellerName, "");
                        dbHelper.insertCartItem(newCartItem);
                        Toast.makeText(getContext(), item.productName + " added to cart", Toast.LENGTH_SHORT).show();
                    }
                });
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
            });
        }
        
        @Override
        public int getItemCount() { return favorites.size(); }
        
        class ViewHolder extends RecyclerView.ViewHolder {
<<<<<<< HEAD
            ImageView ivProductImage, ivDelete, ivCart;
            TextView tvName, tvPrice, tvType;
            ViewHolder(View itemView) {
                super(itemView);
                ivProductImage = itemView.findViewById(R.id.iv_product_image);
=======
            TextView tvName, tvPrice, tvType;
            android.widget.ImageView ivDelete, ivCart;
            ViewHolder(View itemView) {
                super(itemView);
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
                tvName = itemView.findViewById(R.id.tv_product_name);
                tvPrice = itemView.findViewById(R.id.tv_product_price);
                tvType = itemView.findViewById(R.id.tv_product_type);
                ivDelete = itemView.findViewById(R.id.iv_delete);
                ivCart = itemView.findViewById(R.id.iv_cart);
            }
        }
    }
}