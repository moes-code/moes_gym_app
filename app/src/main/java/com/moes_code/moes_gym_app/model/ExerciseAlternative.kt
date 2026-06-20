package com.moes_code.moes_gym_app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "exercise_alternatives",
    primaryKeys = ["exercise_id", "alternative_id"],
    foreignKeys = [
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exercise_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["alternative_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["alternative_id"])
    ]
)
data class ExerciseAlternative(
    @ColumnInfo(name = "exercise_id")
    @SerializedName("exerciseId")
    val exerciseId: Long,

    @ColumnInfo(name = "alternative_id")
    @SerializedName("alternativeId")
    val alternativeId: Long
)
