package com.example.fastmart3.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.fastmart3.R;
import com.example.fastmart3.ui.fragments.buyer.BuyerHomeFragment;
import com.example.fastmart3.ui.fragments.buyer.SearchFragment;
import com.example.fastmart3.ui.fragments.buyer.FavoritesFragment;
import com.example.fastmart3.ui.fragments.buyer.BuyerAccountFragment;
import com.example.fastmart3.ui.activities.CartActivity;

public class BuyerMainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    
    private BottomNavigationView bottomNavigationView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_main);
        
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        
        // Load default fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new BuyerHomeFragment())
                .commit();
    }
    
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        int itemId = item.getItemId();
        
        if (itemId == R.id.nav_home) {
            selectedFragment = new BuyerHomeFragment();
        } else if (itemId == R.id.nav_search) {
            selectedFragment = new SearchFragment();
        } else if (itemId == R.id.nav_favorites) {
            selectedFragment = new FavoritesFragment();
        } else if (itemId == R.id.nav_cart) {
            startActivity(new android.content.Intent(this, CartActivity.class));
            return true;
        } else if (itemId == R.id.nav_account) {
            selectedFragment = new BuyerAccountFragment();
        }
        
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
        }
        return true;
    }
}