package com.moes_code.moes_gym_app.model

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutPlanWithEntries(
    @Embedded
    val plan: WorkoutPlan,

    @Relation(
        parentColumn = "id",
        entityColumn = "plan_id"
    )
    val entries: List<WorkoutPlanEntry>
)
