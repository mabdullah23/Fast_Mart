package com.example.fastmart3.ui.fragments.seller;

import android.content.Intent;
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
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
<<<<<<< HEAD
=======
=======
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.fastmart3.R;
import com.example.fastmart3.models.Product;
import com.example.fastmart3.repositories.ProductRepository;
import com.example.fastmart3.ui.activities.ProductAddActivity;
import com.example.fastmart3.ui.activities.ProductDescriptionActivity;
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
import com.example.fastmart3.utils.ImageUtility;
import com.example.fastmart3.utils.SharedPrefManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerHomeFragment extends Fragment {
    
    private RecyclerView recyclerView;
    private SellerProductAdapter adapter;
<<<<<<< HEAD
=======
=======
import com.example.fastmart3.ui.adapters.ProductAdapter;
import com.example.fastmart3.utils.SharedPrefManager;
import java.util.List;

public class SellerHomeFragment extends Fragment implements ProductAdapter.OnProductClickListener {
    
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
    private ProductRepository productRepository;
    private SharedPrefManager sharedPrefManager;
    private FloatingActionButton fabAddProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_home, container, false);
        
        recyclerView = view.findViewById(R.id.recycler_products);
        fabAddProduct = view.findViewById(R.id.fab_add_product);
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
<<<<<<< HEAD
        adapter = new SellerProductAdapter();
=======
<<<<<<< HEAD
        adapter = new SellerProductAdapter();
=======
        adapter = new ProductAdapter();
        adapter.setOnProductClickListener(this);
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
        recyclerView.setAdapter(adapter);
        
        productRepository = new ProductRepository();
        sharedPrefManager = new SharedPrefManager(getContext());
        
        fabAddProduct.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), ProductAddActivity.class));
        });
        
        loadProducts();
        
        return view;
    }
    
    private void loadProducts() {
        String sellerId = sharedPrefManager.getUserId();
        productRepository.getSellerProducts(sellerId, new ProductRepository.ProductsCallback() {
            @Override
            public void onSuccess(List<Product> products) {
                adapter.setProducts(products);
            }
            @Override
            public void onFailure(String errorMessage) {
<<<<<<< HEAD
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
=======
<<<<<<< HEAD
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
=======
                android.widget.Toast.makeText(getContext(), errorMessage, android.widget.Toast.LENGTH_SHORT).show();
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
            }
        });
    }
    
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
    class SellerProductAdapter extends RecyclerView.Adapter<SellerProductAdapter.ViewHolder> {
        private List<Product> productList;
        private Map<String, Bitmap> imageCache = new HashMap<>();
        
        void setProducts(List<Product> products) {
            this.productList = products;
            notifyDataSetChanged();
        }
        
        private Bitmap getProductImage(Product product) {
            if (imageCache.containsKey(product.getProductId())) {
                return imageCache.get(product.getProductId());
            }
            
            Bitmap bitmap = null;
            if (product.getImageBase64() != null && !TextUtils.isEmpty(product.getImageBase64())) {
                bitmap = ImageUtility.base64ToBitmap(product.getImageBase64());
            }
            
            if (bitmap == null) {
                bitmap = ImageUtility.drawableToBitmap(getContext(), R.drawable.ic_product_default);
            }
            
            imageCache.put(product.getProductId(), bitmap);
            return bitmap;
        }
        
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seller_product, parent, false);
            return new ViewHolder(view);
        }
        
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Product product = productList.get(position);
            
            Bitmap bitmap = getProductImage(product);
            if (bitmap != null) {
                holder.ivProductImage.setImageBitmap(bitmap);
            }
            
            holder.tvName.setText(product.getName());
            holder.tvPrice.setText(String.format("Rs. %.2f", product.getPrice()));
            holder.tvType.setText(product.getType());
            
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), ProductDescriptionActivity.class);
                intent.putExtra("product_id", product.getProductId());
                intent.putExtra("product_name", product.getName());
                intent.putExtra("product_price", product.getPrice());
                intent.putExtra("product_description", product.getDescription());
                intent.putExtra("product_seller", product.getSellerName());
                intent.putExtra("product_type", product.getType());
                intent.putExtra("product_image_base64", product.getImageBase64());
<<<<<<< HEAD
                intent.putExtra("is_seller_view", true);
=======
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
                startActivity(intent);
            });
        }
        
        @Override
        public int getItemCount() { return productList == null ? 0 : productList.size(); }
        
        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivProductImage;
            TextView tvName, tvPrice, tvType;
            ViewHolder(@NonNull View itemView) {
                super(itemView);
                ivProductImage = itemView.findViewById(R.id.iv_product_image);
                tvName = itemView.findViewById(R.id.tv_product_name);
                tvPrice = itemView.findViewById(R.id.tv_product_price);
                tvType = itemView.findViewById(R.id.tv_product_type);
            }
        }
<<<<<<< HEAD
=======
=======
    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(getContext(), ProductDescriptionActivity.class);
        intent.putExtra("product_id", product.getProductId());
        intent.putExtra("product_name", product.getName());
        intent.putExtra("product_price", product.getPrice());
        intent.putExtra("product_description", product.getDescription());
        intent.putExtra("product_seller", product.getSellerName());
        startActivity(intent);
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
    }
}