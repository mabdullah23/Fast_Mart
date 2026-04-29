package com.example.fastmart3.ui.fragments.seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.fastmart3.R;
import com.example.fastmart3.models.Product;
import com.example.fastmart3.repositories.ProductRepository;
import com.example.fastmart3.ui.activities.ProductAddActivity;
import com.example.fastmart3.ui.activities.ProductDescriptionActivity;
import com.example.fastmart3.ui.adapters.ProductAdapter;
import com.example.fastmart3.utils.SharedPrefManager;
import java.util.List;

public class SellerHomeFragment extends Fragment implements ProductAdapter.OnProductClickListener {
    
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ProductRepository productRepository;
    private SharedPrefManager sharedPrefManager;
    private FloatingActionButton fabAddProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_home, container, false);
        
        recyclerView = view.findViewById(R.id.recycler_products);
        fabAddProduct = view.findViewById(R.id.fab_add_product);
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProductAdapter();
        adapter.setOnProductClickListener(this);
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
                android.widget.Toast.makeText(getContext(), errorMessage, android.widget.Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(getContext(), ProductDescriptionActivity.class);
        intent.putExtra("product_id", product.getProductId());
        intent.putExtra("product_name", product.getName());
        intent.putExtra("product_price", product.getPrice());
        intent.putExtra("product_description", product.getDescription());
        intent.putExtra("product_seller", product.getSellerName());
        startActivity(intent);
    }
}