package com.lalosoft.roomdemo.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.lalosoft.roomdemo.database.entity.Product;

/**
 * Created by gonzalo on 7/14/17
 */

@Database(entities = {Product.class}, version = 1)
@TypeConverters({DateTypeConverter.class})
public abstract class MyDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
}
