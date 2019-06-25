package com.example.fhb.pruefungsplaner.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user where Erstpruefer LIKE  :firstName")
    User findByName(String firstName);

    @Query("SELECT COUNT(*) from user")
    int countUsers();

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
