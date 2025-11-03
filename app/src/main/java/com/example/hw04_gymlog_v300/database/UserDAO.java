package com.example.hw04_gymlog_v300.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.hw04_gymlog_v300.database.entities.GymLog;
import com.example.hw04_gymlog_v300.database.entities.User;

import java.util.List;

/**
 * @author Marissa Benenati
 * <br>⋆.˚｡⋆⚘⭒⋆✴︎˚｡⋆
 * <br>COURSE: CST 338 - Software Design
 * <br>DATE: 11/1/2025
 * <br>ASSIGNMENT: GymLog
 */
@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + GymLogDatabase.USER_TABLE + " ORDER BY username")
    LiveData<List<User>> getAllUsers();

    @Query("DELETE from " + GymLogDatabase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + GymLogDatabase.USER_TABLE + " WHERE username == :username")
    LiveData<User> getUserByUserName(String username);

    @Query("SELECT * FROM " + GymLogDatabase.USER_TABLE + " WHERE id == :userId")
    LiveData<User> getUserByUserId(int userId);
}
