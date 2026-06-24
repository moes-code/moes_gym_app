package com.moes_code.moes_gym_app.model

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutPlanWithEntriesAndExercises(
    @Embedded
    val plan: WorkoutPlan,

    @Relation(
        parentColumn = "id",
        entityColumn = "plan_id",
        entity = WorkoutPlanEntry::class
    )
    val entries: List<WorkoutPlanEntryWithExercise>
)

data class WorkoutPlanEntryWithExercise(
    @Embedded
    val entry: WorkoutPlanEntry,

    @Relation(
        parentColumn = "exercise_id",
        entityColumn = "id"
    )
    val exercise: Exercise
)
