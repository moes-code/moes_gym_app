package com.moes_code.moes_gym_app.model

import androidx.room.Embedded
import androidx.room.Relation

data class ExerciseWithSets(
    @Embedded
    val exercise: Exercise,

    @Relation(
        parentColumn = "id",
        entityColumn = "exercise_id"
    )
    val allSets: List<WorkoutSet>
)
