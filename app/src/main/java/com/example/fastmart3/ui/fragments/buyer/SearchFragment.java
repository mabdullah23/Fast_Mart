package com.example.fastmart3.ui.fragments.buyer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fastmart3.R;
import com.example.fastmart3.database.DatabaseHelper;
import com.example.fastmart3.database.entities.CartEntity;
import com.example.fastmart3.database.entities.FavoritesEntity;
import com.example.fastmart3.models.Product;
import com.example.fastmart3.repositories.ProductRepository;
import com.example.fastmart3.ui.activities.ProductDescriptionActivity;
import com.example.fastmart3.ui.adapters.BuyerProductAdapter;
import com.example.fastmart3.utils.SharedPrefManager;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements BuyerProductAdapter.OnProductInteractionListener {
    
    private RecyclerView recyclerView;
    private EditText etSearch;
    private ImageView ivSearch, ivClear;
    private TextView tvNoResults;
    private BuyerProductAdapter adapter;
    private ProductRepository productRepository;
    private DatabaseHelper dbHelper;
    private SharedPrefManager sharedPrefManager;
    private List<Product> allProducts = new ArrayList<>();
    private List<Product> searchResults = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        
        recyclerView = view.findViewById(R.id.recycler_search_results);
        etSearch = view.findViewById(R.id.et_search);
        ivSearch = view.findViewById(R.id.iv_search);
        ivClear = view.findViewById(R.id.iv_clear);
        tvNoResults = view.findViewById(R.id.tv_no_results);
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        adapter = new BuyerProductAdapter();
        adapter.setOnProductInteractionListener(this);
        recyclerView.setAdapter(adapter);
        
        productRepository = new ProductRepository();
        dbHelper = DatabaseHelper.getInstance(getContext());
        sharedPrefManager = new SharedPrefManager(getContext());
        
        loadAllProducts();
        
        ivSearch.setOnClickListener(v -> performSearch());
        ivClear.setOnClickListener(v -> clearSearch());
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            performSearch();
            return true;
        });
        
        return view;
    }
    
    private void loadAllProducts() {
        productRepository.getAllProducts(new ProductRepository.ProductsCallback() {
            @Override
            public void onSuccess(List<Product> products) {
                allProducts = products;
                performSearch();
            }
            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void performSearch() {
        String query = etSearch.getText().toString().trim().toLowerCase();
        searchResults.clear();
        
        if (query.isEmpty()) {
            searchResults.addAll(allProducts);
        } else {
            for (Product product : allProducts) {
                if (product.getName().toLowerCase().contains(query) ||
                    product.getType().toLowerCase().contains(query)) {
                    searchResults.add(product);
                }
            }
        }
        
        // Filter out seller's own products
        String currentUserId = sharedPrefManager.getUserId();
        List<Product> finalResults = new ArrayList<>();
        for (Product p : searchResults) {
            if (!p.getSellerId().equals(currentUserId)) {
                finalResults.add(p);
            }
        }
        
        adapter.setProducts(finalResults, getContext());
        
        if (finalResults.isEmpty()) {
            tvNoResults.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvNoResults.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
    
    private void clearSearch() {
        etSearch.setText("");
        performSearch();
    }
    
    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(getContext(), ProductDescriptionActivity.class);
        intent.putExtra("product_id", product.getProductId());
        intent.putExtra("product_name", product.getName());
        intent.putExtra("product_price", product.getPrice());
        intent.putExtra("product_description", product.getDescription());
        intent.putExtra("product_seller", product.getSellerName());
        intent.putExtra("product_seller_id", product.getSellerId());
        intent.putExtra("product_type", product.getType());
        startActivity(intent);
    }
    
    @Override
    public void onFavoriteClick(Product product) {
        dbHelper.isFavorite(product.getProductId(), new DatabaseHelper.FavoriteStatusCallback() {
            @Override
            public void onResult(boolean isFavorite) {
                if (isFavorite) {
                    dbHelper.deleteFavorite(product.getProductId());
                    Toast.makeText(getContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
                } else {
                    FavoritesEntity fav = new FavoritesEntity(product.getProductId(), product.getName(),
<<<<<<< HEAD
                            product.getType(), product.getPrice(), product.getSellerName(), "");
=======
                            product.getType(), product.getPrice(), product.getSellerName());
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
                    dbHelper.insertFavorite(fav);
                    Toast.makeText(getContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
                }
                performSearch();
            }
        });
    }
    
    @Override
    public void onCartClick(Product product) {
        CartEntity cartItem = new CartEntity(product.getProductId(), product.getName(),
<<<<<<< HEAD
                product.getType(), product.getPrice(), 1, product.getSellerName(), product.getSellerId(), "");
=======
                product.getType(), product.getPrice(), 1, product.getSellerName(), product.getSellerId());
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
        dbHelper.insertCartItem(cartItem);
        Toast.makeText(getContext(), product.getName() + " added to cart", Toast.LENGTH_SHORT).show();
    }
}