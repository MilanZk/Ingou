package com.mkokic.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mkokic.data.ingou.IngouDao
import com.mkokic.data.ingou.IngouDbModel


@Database(
    entities = [
        IngouDbModel::class
    ],
    version = 1, exportSchema = true
)
abstract class IngouDatabase : RoomDatabase() {

    abstract fun ingouDao(): IngouDao

}