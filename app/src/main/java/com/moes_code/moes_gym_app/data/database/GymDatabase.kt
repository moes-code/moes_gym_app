package com.moes_code.moes_gym_app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.moes_code.moes_gym_app.data.dao.ExerciseAlternativeDao
import com.moes_code.moes_gym_app.data.dao.ExerciseDao
import com.moes_code.moes_gym_app.data.dao.WorkoutPlanDao
import com.moes_code.moes_gym_app.data.dao.WorkoutPlanEntryDao
import com.moes_code.moes_gym_app.data.dao.WorkoutSetDao
import com.moes_code.moes_gym_app.data.dao.WorkoutSetTemplateDao
import com.moes_code.moes_gym_app.model.Exercise
import com.moes_code.moes_gym_app.model.ExerciseAlternative
import com.moes_code.moes_gym_app.model.WorkoutPlan
import com.moes_code.moes_gym_app.model.WorkoutPlanEntry
import com.moes_code.moes_gym_app.model.WorkoutSet
import com.moes_code.moes_gym_app.model.WorkoutSetTemplate

@Database(
    entities = [
        Exercise::class,
        WorkoutPlan::class,
        WorkoutPlanEntry::class,
        ExerciseAlternative::class,
        WorkoutSet::class,
        WorkoutSetTemplate::class
    ],
    version = 1,
    exportSchema = false
)
abstract class GymDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
    abstract fun workoutPlanDao(): WorkoutPlanDao
    abstract fun workoutPlanEntryDao(): WorkoutPlanEntryDao
    abstract fun exerciseAlternativeDao(): ExerciseAlternativeDao
    abstract fun workoutSetDao(): WorkoutSetDao
    abstract fun workoutSetTemplateDao(): WorkoutSetTemplateDao

    companion object {
        @Volatile
        private var INSTANCE: GymDatabase? = null

        fun getInstance(context: Context): GymDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    GymDatabase::class.java,
                    "gym_database"
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        SeedData.populate(db)
                    }
                }).build().also { INSTANCE = it }
            }
        }
    }
}
