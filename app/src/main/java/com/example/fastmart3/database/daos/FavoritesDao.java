package com.example.fastmart3.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Delete;
import androidx.room.Query;
import com.example.fastmart3.database.entities.FavoritesEntity;
import java.util.List;

@Dao
public interface FavoritesDao {
    @Insert
    void insert(FavoritesEntity favorite);
    
    @Delete
    void delete(FavoritesEntity favorite);
    
    @Query("SELECT * FROM favorites")
    List<FavoritesEntity> getAllFavorites();
    
    @Query("SELECT * FROM favorites WHERE productId = :productId LIMIT 1")
    FavoritesEntity getFavoriteByProductId(String productId);
    
    @Query("SELECT COUNT(*) FROM favorites WHERE productId = :productId")
    int isFavorite(String productId);
    
    @Query("DELETE FROM favorites WHERE productId = :productId")
    void deleteByProductId(String productId);
}