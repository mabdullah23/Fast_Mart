package com.example.fastmart3.ui.fragments.buyer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class BuyerHomeFragment extends Fragment implements BuyerProductAdapter.OnProductInteractionListener {
    
    private RecyclerView recyclerView;
    private BuyerProductAdapter adapter;
    private ProductRepository productRepository;
    private DatabaseHelper dbHelper;
    private SharedPrefManager sharedPrefManager;
    private List<Product> allProducts = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buyer_home_simple, container, false);
        
        recyclerView = view.findViewById(R.id.recycler_products);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        adapter = new BuyerProductAdapter();
        adapter.setOnProductInteractionListener(this);
        recyclerView.setAdapter(adapter);
        
        productRepository = new ProductRepository();
        dbHelper = DatabaseHelper.getInstance(getContext());
        sharedPrefManager = new SharedPrefManager(getContext());
        
        loadProducts();
        
        return view;
    }
    
    private void loadProducts() {
        productRepository.getAllProducts(new ProductRepository.ProductsCallback() {
            @Override
            public void onSuccess(List<Product> products) {
                allProducts = products;
                // Filter out seller's own products
                String currentUserId = sharedPrefManager.getUserId();
                List<Product> filteredProducts = new ArrayList<>();
                for (Product p : products) {
                    if (!p.getSellerId().equals(currentUserId)) {
                        filteredProducts.add(p);
                    }
                }
                adapter.setProducts(filteredProducts, getContext());
            }
            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
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
                            product.getType(), product.getPrice(), product.getSellerName());
                    dbHelper.insertFavorite(fav);
                    Toast.makeText(getContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
                }
                loadProducts();
            }
        });
    }
    
    @Override
    public void onCartClick(Product product) {
        CartEntity cartItem = new CartEntity(product.getProductId(), product.getName(),
                product.getType(), product.getPrice(), 1, product.getSellerName(), product.getSellerId());
        dbHelper.insertCartItem(cartItem);
        Toast.makeText(getContext(), product.getName() + " added to cart", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (productRepository != null) productRepository.removeListener();
    }
}