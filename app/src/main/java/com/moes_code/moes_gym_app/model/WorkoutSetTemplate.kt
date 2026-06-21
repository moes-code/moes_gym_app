package com.moes_code.moes_gym_app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "workout_set_templates",
    foreignKeys = [
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exercise_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["exercise_id"])]
)
data class WorkoutSetTemplate(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Long = 0,

    @ColumnInfo(name = "exercise_id")
    @SerializedName("exerciseId")
    val exerciseId: Long,

    @ColumnInfo(name = "set_number")
    @SerializedName("setNumber")
    val setNumber: Int,

    @ColumnInfo(name = "reps")
    @SerializedName("reps")
    val reps: Int,

    @ColumnInfo(name = "rest_time")
    @SerializedName("restTime")
    val restTime: Int
)
