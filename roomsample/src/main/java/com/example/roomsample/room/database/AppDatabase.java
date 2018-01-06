package com.example.roomsample.room.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.roomsample.room.Converters;
import com.example.roomsample.room.bean.User;
import com.example.roomsample.room.dao.UserDao;

@Database(entities = {User.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
//    public abstract MyDao myDao();
}