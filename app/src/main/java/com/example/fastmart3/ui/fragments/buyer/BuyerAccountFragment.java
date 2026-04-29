package com.example.fastmart3.ui.fragments.buyer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
<<<<<<< HEAD
import androidx.annotation.NonNull;
=======
<<<<<<< HEAD
import androidx.annotation.NonNull;
=======
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.fastmart3.R;
import com.example.fastmart3.ui.activities.LoginActivity;
<<<<<<< HEAD
import com.example.fastmart3.ui.fragments.ThemeSettingsFragment;
=======
<<<<<<< HEAD
import com.example.fastmart3.ui.fragments.ThemeSettingsFragment;
=======
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
import com.example.fastmart3.utils.SharedPrefManager;

public class BuyerAccountFragment extends Fragment {
    
    private TextView tvName, tvEmail, tvPhone, tvAddress, tvCountry, tvGender, tvDob;
<<<<<<< HEAD
    private Button btnLogout, btnTheme;
=======
<<<<<<< HEAD
    private Button btnLogout, btnTheme;
=======
    private Button btnLogout;
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
    private SharedPrefManager sharedPrefManager;
    private DatabaseReference userRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buyer_account, container, false);
        
        tvName = view.findViewById(R.id.tv_name);
        tvEmail = view.findViewById(R.id.tv_email);
        tvPhone = view.findViewById(R.id.tv_phone);
        tvAddress = view.findViewById(R.id.tv_address);
        tvCountry = view.findViewById(R.id.tv_country);
        tvGender = view.findViewById(R.id.tv_gender);
        tvDob = view.findViewById(R.id.tv_dob);
        btnLogout = view.findViewById(R.id.btn_logout);
<<<<<<< HEAD
        btnTheme = view.findViewById(R.id.btn_theme);
=======
<<<<<<< HEAD
        btnTheme = view.findViewById(R.id.btn_theme);
=======
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
        
        sharedPrefManager = new SharedPrefManager(getContext());
        String userId = sharedPrefManager.getUserId();
        userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
        
        loadUserData();
        
        btnLogout.setOnClickListener(v -> logout());
<<<<<<< HEAD
        btnTheme.setOnClickListener(v -> openThemeSettings());
=======
<<<<<<< HEAD
        btnTheme.setOnClickListener(v -> openThemeSettings());
=======
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
        
        return view;
    }
    
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
    private void openThemeSettings() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ThemeSettingsFragment())
                .addToBackStack(null)
                .commit();
    }
    
    private void loadUserData() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
<<<<<<< HEAD
=======
=======
    private void loadUserData() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
                tvName.setText("Name: " + getValue(snapshot, "name"));
                tvEmail.setText("Email: " + getValue(snapshot, "email"));
                tvPhone.setText("Phone: " + getValue(snapshot, "phone"));
                tvAddress.setText("Address: " + getValue(snapshot, "address"));
                tvCountry.setText("Country: " + getValue(snapshot, "country"));
                tvGender.setText("Gender: " + getValue(snapshot, "gender"));
                tvDob.setText("DOB: " + getValue(snapshot, "dateOfBirth"));
            }
            @Override
<<<<<<< HEAD
            public void onCancelled(@NonNull DatabaseError error) {
=======
<<<<<<< HEAD
            public void onCancelled(@NonNull DatabaseError error) {
=======
            public void onCancelled(DatabaseError error) {
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
                Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private String getValue(DataSnapshot snapshot, String key) {
        Object value = snapshot.child(key).getValue();
        return value != null ? value.toString() : "Not Set";
    }
    
    private void logout() {
        sharedPrefManager.logout();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().finish();
    }
}