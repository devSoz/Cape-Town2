package com.example.myapplication.Database;
import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.Models.favHero;

import java.util.List;

@androidx.room.Dao
public interface interfaceDb {

    @Insert
    void insert(favHero model);

    @Update
    void update(favHero model);
    @Delete
    void delete(favHero model);

    @Query("DELETE FROM FavHero WHERE heroId = :heroId")
    void deleteById(Integer heroId);

    @Query("select * from FavHero ORDER BY id ASC")
    List<favHero>getFavHero();

    //@Query("SELECT * FROM FaveDoge ORDER BY id ASC")
    //LiveData<List<favDoge>> getFavDoge();
}

