package com.moes_code.moes_gym_app.data.repository

import androidx.room.withTransaction
import com.moes_code.moes_gym_app.data.dao.ExerciseAlternativeDao
import com.moes_code.moes_gym_app.data.dao.ExerciseDao
import com.moes_code.moes_gym_app.data.dao.WorkoutPlanDao
import com.moes_code.moes_gym_app.data.dao.WorkoutPlanEntryDao
import com.moes_code.moes_gym_app.data.dao.WorkoutSetDao
import com.moes_code.moes_gym_app.data.dao.WorkoutSetTemplateDao
import com.moes_code.moes_gym_app.data.database.GymDatabase
import com.moes_code.moes_gym_app.model.Exercise
import com.moes_code.moes_gym_app.model.WorkoutPlan
import com.moes_code.moes_gym_app.model.WorkoutPlanEntry
import com.moes_code.moes_gym_app.model.WorkoutPlanWithEntriesAndExercises
import com.moes_code.moes_gym_app.model.WorkoutSet
import com.moes_code.moes_gym_app.model.WorkoutSetTemplate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class GymRepository(private val db: GymDatabase) {

    private val exerciseDao: ExerciseDao = db.exerciseDao()
    private val workoutPlanDao: WorkoutPlanDao = db.workoutPlanDao()
    private val workoutPlanEntryDao: WorkoutPlanEntryDao = db.workoutPlanEntryDao()
    private val exerciseAlternativeDao: ExerciseAlternativeDao = db.exerciseAlternativeDao()
    private val workoutSetDao: WorkoutSetDao = db.workoutSetDao()
    private val workoutSetTemplateDao: WorkoutSetTemplateDao = db.workoutSetTemplateDao()

    fun getAllPlans(): Flow<List<WorkoutPlan>> = workoutPlanDao.getAllPlans()

    fun getPlanWithEntriesAndExercises(planId: Long): Flow<WorkoutPlanWithEntriesAndExercises?> {
        return workoutPlanDao.getPlanWithEntriesAndExercises(planId)
    }

    fun getAlternativesSortedByLastTrained(exerciseId: Long): Flow<List<Exercise>> {
        return exerciseAlternativeDao.getAlternativesSortedByLastTrained(exerciseId)
    }

    fun getSetsForExercise(exerciseId: Long): Flow<List<WorkoutSet>> {
        return workoutSetDao.getSetsForExercise(exerciseId)
    }

    suspend fun getTemplatesForExercise(exerciseId: Long): List<WorkoutSetTemplate> {
        return workoutSetTemplateDao.getTemplatesForExercise(exerciseId)
    }

    suspend fun rotateExerciseIfNeeded(planId: Long) {
        val plan = workoutPlanDao.getPlanWithEntriesAndExercises(planId).first() ?: return

        db.withTransaction {
            for (entryWithExercise in plan.entries) {
                val currentExerciseId = entryWithExercise.entry.exerciseId
                val alternatives = exerciseAlternativeDao
                    .getAlternativesSortedByLastTrained(currentExerciseId)
                    .first()

                if (alternatives.isEmpty()) continue

                val oldestId = alternatives.first().id
                workoutPlanEntryDao.updateExerciseId(planId, entryWithExercise.entry.position, oldestId)
            }
        }
    }

    suspend fun selectAlternative(planId: Long, position: Int, newExerciseId: Long) {
        workoutPlanEntryDao.updateExerciseId(planId, position, newExerciseId)
    }

    suspend fun logSets(exerciseId: Long, sets: List<WorkoutSet>) {
        db.withTransaction {
            for (set in sets) {
                workoutSetDao.insert(set)
            }
            exerciseDao.updateLastTrainedAt(exerciseId, System.currentTimeMillis())
        }
    }

    suspend fun insertExercise(exercise: Exercise): Long = exerciseDao.insert(exercise)

    suspend fun updateExercise(exercise: Exercise) = exerciseDao.update(exercise)

    suspend fun insertWorkoutPlan(plan: WorkoutPlan): Long = workoutPlanDao.insert(plan)

    suspend fun insertWorkoutPlanEntry(entry: WorkoutPlanEntry): Long = workoutPlanEntryDao.insert(entry)
}
