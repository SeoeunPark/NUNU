package com.nunu.NUNU;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface UserDao {

    @Query("SELECT * FROM UserInfo")
    List<UserInfo> getAll();

    @Query("SELECT leftSight FROM UserInfo ORDER BY id DESC limit 1")
    String getLeft();

    @Query("SELECT rightSight FROM UserInfo ORDER BY id DESC limit 1")
    String getRight();

    @Query("SELECT username FROM UserInfo ORDER BY id DESC limit 1")
    String getName();

    @Query("SELECT date FROM UserInfo ORDER BY id DESC limit 1")
    String getDate();

    @Insert
    void insert(UserInfo userInfo);

    @Update
    void update(UserInfo userInfo);

    @Delete
    void delete(UserInfo userInfo);

    @Query("DELETE FROM UserInfo")
    void deleteAll();

}
