package com.example.fastmart3.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.google.android.material.navigation.NavigationView;
import com.example.fastmart3.R;
import com.example.fastmart3.ui.fragments.seller.SellerHomeFragment;
import com.example.fastmart3.ui.fragments.seller.SellerOrderHistoryFragment;
import com.example.fastmart3.ui.fragments.seller.SellerAccountFragment;
import com.example.fastmart3.utils.SharedPrefManager;

public class SellerMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    
    private DrawerLayout drawerLayout;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_main);
        
        sharedPrefManager = new SharedPrefManager(this);
        
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        
        navigationView.setNavigationItemSelectedListener(this);
        
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SellerHomeFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }
    
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        int id = item.getItemId();
        
        if (id == R.id.nav_home) fragment = new SellerHomeFragment();
        else if (id == R.id.nav_orders) fragment = new SellerOrderHistoryFragment();
        else if (id == R.id.nav_account) fragment = new SellerAccountFragment();
        else if (id == R.id.nav_logout) {
            sharedPrefManager.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        }
        
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
        
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }
}
