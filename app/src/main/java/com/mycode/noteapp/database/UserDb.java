package com.mycode.noteapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.mycode.noteapp.Dao.UserDAO;
import com.mycode.noteapp.Entities.User;

@Database(entities ={User.class} ,version = 1)
public abstract class UserDb extends RoomDatabase {
    private static final String dbName ="user";

    private static UserDb userDB;

    public static synchronized UserDb getUserDB(Context context){
        if (userDB==null){
            userDB = Room.databaseBuilder(context,
                    UserDb.class ,
                    dbName).fallbackToDestructiveMigration().build();
        } return userDB;
    }
    public abstract UserDAO userDao();
}
