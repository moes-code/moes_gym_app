package com.moes_code.moes_gym_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.moes_code.moes_gym_app.model.WorkoutSetTemplate

@Dao
interface WorkoutSetTemplateDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(template: WorkoutSetTemplate): Long

    @Update
    suspend fun update(template: WorkoutSetTemplate)

    @Delete
    suspend fun delete(template: WorkoutSetTemplate)

    @Query("SELECT * FROM workout_set_templates WHERE exercise_id = :exerciseId ORDER BY set_number ASC")
    suspend fun getTemplatesForExercise(exerciseId: Long): List<WorkoutSetTemplate>

    @Query("SELECT * FROM workout_set_templates WHERE exercise_id IN (:exerciseIds) ORDER BY exercise_id, set_number ASC")
    suspend fun getTemplatesForExercises(exerciseIds: List<Long>): List<WorkoutSetTemplate>

    @Query("DELETE FROM workout_set_templates WHERE exercise_id = :exerciseId")
    suspend fun deleteTemplatesForExercise(exerciseId: Long)
}
