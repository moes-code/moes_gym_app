package com.moes_code.moes_gym_app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Long = 0,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String,

    @ColumnInfo(name = "note")
    @SerializedName("note")
    val note: String? = null,

    @ColumnInfo(name = "last_trained_at")
    @SerializedName("lastTrainedAt")
    val lastTrainedAt: Long? = null
)
