package com.moes_code.moes_gym_app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "workout_sets",
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
data class WorkoutSet(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Long = 0,

    @ColumnInfo(name = "exercise_id")
    @SerializedName("exerciseId")
    val exerciseId: Long,

    @ColumnInfo(name = "timestamp")
    @SerializedName("timestamp")
    val timestamp: Long,

    @ColumnInfo(name = "number")
    @SerializedName("number")
    val number: Int,

    @ColumnInfo(name = "weight")
    @SerializedName("weight")
    val weight: Double,

    @ColumnInfo(name = "reps")
    @SerializedName("reps")
    val reps: Int,

    @ColumnInfo(name = "rest_time")
    @SerializedName("restTime")
    val restTime: Int // in seconds
)
