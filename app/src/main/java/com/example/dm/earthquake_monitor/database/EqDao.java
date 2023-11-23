package com.example.dm.earthquake_monitor.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dm.earthquake_monitor.Earthquake;

import java.util.List;

@Dao
public interface EqDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Earthquake> eqList);
    @Query("SELECT * FROM earthquakes")
    List<Earthquake> getEarthQuakes();

    /*@Query("SELECT * FROM earthquakes WHERE magnitude > :myMagnitude ")
    List<Earthquake> getEarthQuakesWithMagnitudeAbove( double myMagnitude);
    @Delete
    void deleteEarthQuake(Earthquake earthquake);
    @Update
    void updateEarthQuake(Earthquake earthquake);*/

}
