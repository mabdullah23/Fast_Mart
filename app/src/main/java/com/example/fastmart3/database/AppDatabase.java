package com.example.fastmart3.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.fastmart3.database.daos.FavoritesDao;
import com.example.fastmart3.database.daos.CartDao;
import com.example.fastmart3.database.entities.FavoritesEntity;
import com.example.fastmart3.database.entities.CartEntity;

@Database(entities = {FavoritesEntity.class, CartEntity.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavoritesDao favoritesDao();
    public abstract CartDao cartDao();

    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "fastmart3_database")
                    .fallbackToDestructiveMigration()  // This will recreate the database
                    .build();
        }
        return instance;
    }
}