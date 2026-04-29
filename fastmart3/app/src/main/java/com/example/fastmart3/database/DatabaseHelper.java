package com.example.fastmart3.database;

import android.content.Context;
import android.os.AsyncTask;
import com.example.fastmart3.database.entities.FavoritesEntity;
import com.example.fastmart3.database.entities.CartEntity;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseHelper {
    private static DatabaseHelper instance;
    private AppDatabase database;
    private ExecutorService executorService;
    
    private DatabaseHelper(Context context) {
        database = AppDatabase.getInstance(context);
        executorService = Executors.newSingleThreadExecutor();
    }
    
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }
    
    public interface FavoritesCallback {
        void onSuccess(List<FavoritesEntity> favorites);
        void onError(String error);
    }
    
    public interface FavoriteStatusCallback {
        void onResult(boolean isFavorite);
    }
    
    public interface CartCallback {
        void onSuccess(List<CartEntity> cartItems);
        void onError(String error);
    }
    
    public interface CartTotalCallback {
        void onSuccess(double total);
    }
    
    public interface CartItemCallback {
        void onResult(CartEntity cartItem);
    }
    
    // Favorites operations
    public void getAllFavorites(FavoritesCallback callback) {
        executorService.execute(() -> {
            try {
                List<FavoritesEntity> favorites = database.favoritesDao().getAllFavorites();
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    callback.onSuccess(favorites);
                });
            } catch (Exception e) {
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    callback.onError(e.getMessage());
                });
            }
        });
    }
    
    public void isFavorite(String productId, FavoriteStatusCallback callback) {
        executorService.execute(() -> {
            try {
                int count = database.favoritesDao().isFavorite(productId);
                boolean isFav = count > 0;
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    callback.onResult(isFav);
                });
            } catch (Exception e) {
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    callback.onResult(false);
                });
            }
        });
    }
    
    public void insertFavorite(FavoritesEntity favorite) {
        executorService.execute(() -> {
            database.favoritesDao().insert(favorite);
        });
    }
    
    public void deleteFavorite(String productId) {
        executorService.execute(() -> {
            database.favoritesDao().deleteByProductId(productId);
        });
    }
    
    // Cart operations
    public void getAllCartItems(CartCallback callback) {
        executorService.execute(() -> {
            try {
                List<CartEntity> cartItems = database.cartDao().getAllCartItems();
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    callback.onSuccess(cartItems);
                });
            } catch (Exception e) {
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    callback.onError(e.getMessage());
                });
            }
        });
    }
    
    public void getCartItem(String productId, CartItemCallback callback) {
        executorService.execute(() -> {
            try {
                CartEntity item = database.cartDao().getCartItemByProductId(productId);
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    callback.onResult(item);
                });
            } catch (Exception e) {
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    callback.onResult(null);
                });
            }
        });
    }
    
    public void insertCartItem(CartEntity cartItem) {
        executorService.execute(() -> {
            database.cartDao().insert(cartItem);
        });
    }
    
    public void updateCartItem(CartEntity cartItem) {
        executorService.execute(() -> {
            database.cartDao().update(cartItem);
        });
    }
    
    public void deleteCartItem(CartEntity cartItem) {
        executorService.execute(() -> {
            database.cartDao().delete(cartItem);
        });
    }
    
    public void clearCart() {
        executorService.execute(() -> {
            database.cartDao().clearCart();
        });
    }
    
    public void getTotalCartPrice(CartTotalCallback callback) {
        executorService.execute(() -> {
            try {
                double total = database.cartDao().getTotalCartPrice();
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    callback.onSuccess(total);
                });
            } catch (Exception e) {
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    callback.onSuccess(0);
                });
            }
        });
    }
}