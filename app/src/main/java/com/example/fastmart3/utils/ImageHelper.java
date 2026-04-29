package com.example.fastmart3.utils;

import android.content.Context;
import com.example.fastmart3.R;

public class ImageHelper {
    
    public static int getImageForProductType(String type) {
        if (type == null) return R.drawable.ic_product_default;
        
        switch (type.toLowerCase()) {
            case "electronics": return R.drawable.ic_product_electronics;
            case "clothing": return R.drawable.ic_product_clothing;
            case "food": return R.drawable.ic_product_food;
            case "books": return R.drawable.ic_product_books;
            case "sports": return R.drawable.ic_product_sports;
            default: return R.drawable.ic_product_default;
        }
    }
    
    public static int getImageForProduct(int imageDrawable) {
        if (imageDrawable != 0) {
            return imageDrawable;
        }
        return R.drawable.ic_product_default;
    }
}