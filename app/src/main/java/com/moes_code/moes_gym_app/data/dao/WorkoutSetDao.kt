package com.moes_code.moes_gym_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.moes_code.moes_gym_app.model.WorkoutSet
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutSetDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(set: WorkoutSet): Long

    @Update
    suspend fun update(set: WorkoutSet)

    @Delete
    suspend fun delete(set: WorkoutSet)

    @Query("SELECT * FROM workout_sets WHERE exercise_id = :exerciseId ORDER BY timestamp DESC")
    fun getSetsForExercise(exerciseId: Long): Flow<List<WorkoutSet>>
}
