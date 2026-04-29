package com.example.fastmart3.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Delete;
import androidx.room.Update;
import androidx.room.Query;
import com.example.fastmart3.database.entities.CartEntity;
import java.util.List;

@Dao
public interface CartDao {
    @Insert
    void insert(CartEntity cartItem);
    
    @Delete
    void delete(CartEntity cartItem);
    
    @Update
    void update(CartEntity cartItem);
    
    @Query("SELECT * FROM cart")
    List<CartEntity> getAllCartItems();
    
    @Query("SELECT * FROM cart WHERE productId = :productId LIMIT 1")
    CartEntity getCartItemByProductId(String productId);
    
    @Query("UPDATE cart SET quantity = :quantity WHERE productId = :productId")
    void updateQuantity(String productId, int quantity);
    
    @Query("DELETE FROM cart WHERE productId = :productId")
    void deleteByProductId(String productId);
    
    @Query("DELETE FROM cart")
    void clearCart();
    
    @Query("SELECT SUM(productPrice * quantity) FROM cart")
    double getTotalCartPrice();
}