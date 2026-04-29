package com.example.fastmart3.ui.fragments.seller;

import android.content.Intent;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.fastmart3.R;
import com.example.fastmart3.models.Product;
import com.example.fastmart3.repositories.ProductRepository;
import com.example.fastmart3.ui.activities.ProductAddActivity;
import com.example.fastmart3.ui.activities.ProductDescriptionActivity;
import com.example.fastmart3.utils.ImageUtility;
import com.example.fastmart3.utils.SharedPrefManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerHomeFragment extends Fragment {
    
    private RecyclerView recyclerView;
    private SellerProductAdapter adapter;
    private ProductRepository productRepository;
    private SharedPrefManager sharedPrefManager;
    private FloatingActionButton fabAddProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_home, container, false);
        
        recyclerView = view.findViewById(R.id.recycler_products);
        fabAddProduct = view.findViewById(R.id.fab_add_product);
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SellerProductAdapter();
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
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
    
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
    }
}