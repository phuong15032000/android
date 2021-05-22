package com.mycode.noteapp.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.mycode.noteapp.Entities.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void registerUser(User user);
    @Query("SELECT * FROM user")
    User login1();
    @Query("SELECT * FROM user where password=(:password)")
    User login2(String password);
}
