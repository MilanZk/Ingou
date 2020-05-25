package com.mkokic.data.ingou

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "INGOU_TABLE")
data class IngouDbModel(
    @PrimaryKey
    val idIngou: Int,
    val name: String
)