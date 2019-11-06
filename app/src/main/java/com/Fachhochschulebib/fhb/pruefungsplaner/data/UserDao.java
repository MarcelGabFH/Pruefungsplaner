package com.Fachhochschulebib.fhb.pruefungsplaner.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;



@Dao
public interface UserDao {


    @Query("SELECT * FROM user WHERE Validation = :validation")
    List<User> getAll(String validation);

    @Query("SELECT * FROM user")
    List<User> getAll2();

    @Query("SELECT Studiengang FROM user")
    List<String> getStudiengang();

    @Query("SELECT Erstpruefer FROM user")
    List<String> getErstpruefer();

    @Query("SELECT Modul FROM user")
    List<String> getModul();

    @Query("SELECT COUNT(*) from user")
    int countUsers();

    @Insert
    void insertAll(User... users);

    @Query ("UPDATE User SET Favorit = :favorit WHERE ID = :id")
    void update(boolean favorit, int id );

    @Query ("UPDATE User SET Ausgewaehlt = :pruefungen WHERE ID = :id")
    void update2(boolean pruefungen,int id);

    @Query ("UPDATE User SET Validation  = :nullSetzen WHERE Validation = :validation")
    void updateValidation(String nullSetzen, String validation);

    @Query ("UPDATE User SET Ausgewaehlt = :pruefungen ")
    void suchezuruecksetzen(boolean pruefungen);

    @Delete
    void delete(User user);
}
