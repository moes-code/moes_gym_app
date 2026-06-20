package com.moes_code.moes_gym_app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "workout_plan_entries",
    primaryKeys = ["plan_id", "position"],
    foreignKeys = [
        ForeignKey(
            entity = WorkoutPlan::class,
            parentColumns = ["id"],
            childColumns = ["plan_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exercise_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["plan_id"]),
        Index(value = ["exercise_id"])
    ]
)
data class WorkoutPlanEntry(
    @ColumnInfo(name = "plan_id")
    @SerializedName("planId")
    val planId: Long,

    @ColumnInfo(name = "position")
    @SerializedName("position")
    val position: Int,

    @ColumnInfo(name = "exercise_id")
    @SerializedName("exerciseId")
    val exerciseId: Long
)
