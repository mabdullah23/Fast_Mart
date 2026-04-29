package com.example.fastmart3.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.fastmart3.R;
import com.example.fastmart3.utils.ThemeManager;

public class ThemeSettingsFragment extends Fragment {
    
    private RadioGroup radioGroupTheme;
    private RadioButton rbLight, rbDark, rbSystem;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theme_settings, container, false);
        
        radioGroupTheme = view.findViewById(R.id.radio_group_theme);
        rbLight = view.findViewById(R.id.rb_light);
        rbDark = view.findViewById(R.id.rb_dark);
        rbSystem = view.findViewById(R.id.rb_system);
        
        int currentTheme = ThemeManager.getThemeMode(getContext());
        switch (currentTheme) {
            case ThemeManager.THEME_DARK:
                rbDark.setChecked(true);
                break;
            case ThemeManager.THEME_SYSTEM:
                rbSystem.setChecked(true);
                break;
            default:
                rbLight.setChecked(true);
                break;
        }
        
        radioGroupTheme.setOnCheckedChangeListener((group, checkedId) -> {
            int themeMode = ThemeManager.THEME_LIGHT;
            if (checkedId == R.id.rb_dark) {
                themeMode = ThemeManager.THEME_DARK;
            } else if (checkedId == R.id.rb_system) {
                themeMode = ThemeManager.THEME_SYSTEM;
            }
            
            ThemeManager.saveTheme(getContext(), themeMode);
            Toast.makeText(getContext(), "Theme changed. Restarting activity...", Toast.LENGTH_SHORT).show();
            
            // Restart activity to apply theme
            requireActivity().recreate();
        });
        
        return view;
    }
}