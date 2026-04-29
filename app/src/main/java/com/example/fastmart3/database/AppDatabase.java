package com.example.fastmart3.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.fastmart3.database.daos.FavoritesDao;
import com.example.fastmart3.database.daos.CartDao;
import com.example.fastmart3.database.entities.FavoritesEntity;
import com.example.fastmart3.database.entities.CartEntity;

<<<<<<< HEAD
@Database(entities = {FavoritesEntity.class, CartEntity.class}, version = 2, exportSchema = false)
=======
<<<<<<< HEAD
@Database(entities = {FavoritesEntity.class, CartEntity.class}, version = 2, exportSchema = false)
=======
@Database(entities = {FavoritesEntity.class, CartEntity.class}, version = 1, exportSchema = false)
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavoritesDao favoritesDao();
    public abstract CartDao cartDao();

    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "fastmart3_database")
<<<<<<< HEAD
                    .fallbackToDestructiveMigration()  // This will recreate the database
=======
<<<<<<< HEAD
                    .fallbackToDestructiveMigration()  // This will recreate the database
=======
                    .fallbackToDestructiveMigration()
>>>>>>> 560c833ca4b36c9c927e21a5fcd8960f89d7c3b2
>>>>>>> db181f6f4caed1408fd3e8c62f6c4739051084ec
                    .build();
        }
        return instance;
    }
}