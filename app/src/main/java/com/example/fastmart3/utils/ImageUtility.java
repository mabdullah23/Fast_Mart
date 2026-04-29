package com.example.fastmart3.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import androidx.core.content.ContextCompat;
import java.io.ByteArrayOutputStream;

public class ImageUtility {
    
    // Convert Bitmap to Base64 string
    public static String bitmapToBase64(Bitmap bitmap) {
        if (bitmap == null) return null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
    
    // Convert Base64 string to Bitmap
    public static Bitmap base64ToBitmap(String base64String) {
        if (base64String == null || base64String.isEmpty()) return null;
        try {
            byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        } catch (Exception e) {
            return null;
        }
    }
    
    // Compress and resize bitmap for storage
    public static Bitmap resizeBitmap(Bitmap bitmap, int maxWidth, int maxHeight) {
        if (bitmap == null) return null;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        
        if (width > maxWidth) {
            float ratio = (float) maxWidth / width;
            width = maxWidth;
            height = (int) (height * ratio);
        }
        
        if (height > maxHeight) {
            float ratio = (float) maxHeight / height;
            height = maxHeight;
            width = (int) (width * ratio);
        }
        
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }
    
    // Convert drawable resource to Bitmap
    public static Bitmap drawableToBitmap(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        drawable.setBounds(0, 0, 100, 100);
        drawable.draw(new android.graphics.Canvas(bitmap));
        return bitmap;
    }
}