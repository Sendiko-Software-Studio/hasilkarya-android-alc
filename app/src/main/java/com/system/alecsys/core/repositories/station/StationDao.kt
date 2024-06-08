package com.system.alecsys.core.repositories.station

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.system.alecsys.core.entities.StationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(station: StationEntity)

    @Update
    suspend fun update(station: StationEntity)

    @Delete
    suspend fun delete(station: StationEntity)

    @Query("SELECT * FROM stations")
    fun getAll(): Flow<List<StationEntity>>

    @Query("SELECT * FROM stations WHERE id = :id")
    fun getById(id: Int): StationEntity

}