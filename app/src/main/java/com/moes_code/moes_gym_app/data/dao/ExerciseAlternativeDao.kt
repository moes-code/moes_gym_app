package com.moes_code.moes_gym_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moes_code.moes_gym_app.model.Exercise
import com.moes_code.moes_gym_app.model.ExerciseAlternative
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseAlternativeDao {
    // IGNORE: silently skip duplicates from bidirectional pair storage
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(alternative: ExerciseAlternative)

    @Query("DELETE FROM exercise_alternatives WHERE exercise_id = :exerciseId AND alternative_id = :alternativeId")
    suspend fun removeAlternative(exerciseId: Long, alternativeId: Long)

    @Query(
        """
        SELECT DISTINCT e.* FROM exercises e
        JOIN exercise_alternatives ea ON 
            (ea.exercise_id = e.id OR ea.alternative_id = e.id)
        WHERE (ea.exercise_id = :exerciseId OR ea.alternative_id = :exerciseId)
          AND e.id != :exerciseId
        ORDER BY 
            CASE WHEN e.last_trained_at IS NULL THEN 0 ELSE 1 END,
            e.last_trained_at ASC,
            e.name ASC
        """
    )
    fun getAlternativesSortedByLastTrained(exerciseId: Long): Flow<List<Exercise>>
}
