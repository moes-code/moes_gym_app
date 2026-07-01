package com.moes_code.moes_gym_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.moes_code.moes_gym_app.model.WorkoutPlanEntry

@Dao
interface WorkoutPlanEntryDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entry: WorkoutPlanEntry): Long

    @Update
    suspend fun update(entry: WorkoutPlanEntry)

    @Delete
    suspend fun delete(entry: WorkoutPlanEntry)

    @Query("SELECT * FROM workout_plan_entries WHERE plan_id = :planId ORDER BY position ASC")
    suspend fun getEntriesForPlan(planId: Long): List<WorkoutPlanEntry>

    @Query("SELECT COALESCE(MAX(position), 0) + 1 FROM workout_plan_entries WHERE plan_id = :planId")
    suspend fun getNextPosition(planId: Long): Int

    @Query("DELETE FROM workout_plan_entries WHERE plan_id = :planId AND position = :position")
    suspend fun deleteByPlanIdAndPosition(planId: Long, position: Int)

    @Query("UPDATE workout_plan_entries SET exercise_id = :exerciseId WHERE plan_id = :planId AND position = :position")
    suspend fun updateExerciseId(planId: Long, position: Int, exerciseId: Long): Int
}
