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
<<<<<<< HEAD
import com.example.fastmart3.ui.fragments.seller.SellerChatListFragment;
import com.example.fastmart3.ui.fragments.ThemeSettingsFragment;
=======
<<<<<<< HEAD
import com.example.fastmart3.ui.fragments.seller.SellerChatListFragment;
import com.example.fastmart3.ui.fragments.ThemeSettingsFragment;
=======
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
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
        
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
        if (id == R.id.nav_home) {
            fragment = new SellerHomeFragment();
        } else if (id == R.id.nav_orders) {
            fragment = new SellerOrderHistoryFragment();
        } else if (id == R.id.nav_chats) {
            fragment = new SellerChatListFragment();
        } else if (id == R.id.nav_theme) {
            fragment = new ThemeSettingsFragment();
        } else if (id == R.id.nav_account) {
            fragment = new SellerAccountFragment();
        } else if (id == R.id.nav_logout) {
<<<<<<< HEAD
=======
=======
        if (id == R.id.nav_home) fragment = new SellerHomeFragment();
        else if (id == R.id.nav_orders) fragment = new SellerOrderHistoryFragment();
        else if (id == R.id.nav_account) fragment = new SellerAccountFragment();
        else if (id == R.id.nav_logout) {
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
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
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
<<<<<<< HEAD
}
=======
}
=======
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }
}
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
