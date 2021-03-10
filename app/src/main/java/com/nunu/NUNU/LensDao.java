package com.nunu.NUNU;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LensDao {
    @Query("SELECT * FROM lens_table")
    List<Note> getAll();

    //@Query 사용하여 쿼리문 작성하기

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note lensInfo);

    @Query("DELETE FROM lens_table")
    void deleteAll();


    @Query("SELECT * from lens_table ORDER BY word ASC")
    LiveData<List<Note>> getAllWords();

    @Query("SELECT COUNT(*) from lens_table")
    int getNum();
}