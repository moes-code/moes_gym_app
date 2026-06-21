package com.moes_code.moes_gym_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.moes_code.moes_gym_app.model.WorkoutPlan
import com.moes_code.moes_gym_app.model.WorkoutPlanWithEntriesAndExercises
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutPlanDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(plan: WorkoutPlan): Long

    @Update
    suspend fun update(plan: WorkoutPlan)

    @Delete
    suspend fun delete(plan: WorkoutPlan)

    @Query("SELECT * FROM workout_plans ORDER BY name ASC")
    fun getAllPlans(): Flow<List<WorkoutPlan>>

    @Transaction
    @Query("SELECT * FROM workout_plans WHERE id = :id")
    fun getPlanWithEntriesAndExercises(id: Long): Flow<WorkoutPlanWithEntriesAndExercises?>
}
