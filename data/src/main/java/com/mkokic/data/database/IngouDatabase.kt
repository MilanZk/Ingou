package com.mkokic.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mkokic.data.ingou.local.IngouDao
import com.mkokic.data.ingou.local.IngouDbModel


@Database(
    entities = [
        IngouDbModel::class
    ],
    version = 1
)
abstract class IngouDatabase : RoomDatabase() {

    abstract fun ingouDao(): IngouDao

}