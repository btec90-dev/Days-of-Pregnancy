package com.example.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PregnancyDao {
    @Query("SELECT * FROM pregnancy_state WHERE id = 1")
    fun getPregnancyState(): Flow<PregnancyStateEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPregnancyState(state: PregnancyStateEntity)
}
