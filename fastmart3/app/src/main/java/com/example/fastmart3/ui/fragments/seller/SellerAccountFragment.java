package com.example.fastmart3.ui.fragments.seller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.fastmart3.R;
import com.example.fastmart3.utils.SharedPrefManager;

public class SellerAccountFragment extends Fragment {
    
    private TextView tvName, tvEmail;
    private SharedPrefManager sharedPrefManager;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_account, container, false);
        
        tvName = view.findViewById(R.id.tv_name);
        tvEmail = view.findViewById(R.id.tv_email);
        
        sharedPrefManager = new SharedPrefManager(getContext());
        
        tvName.setText("Name: " + sharedPrefManager.getUserName());
        tvEmail.setText("Email: " + sharedPrefManager.getUserEmail());
        
        return view;
    }
}