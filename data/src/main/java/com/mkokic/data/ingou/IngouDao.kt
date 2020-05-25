package com.mkokic.data.ingou

import androidx.room.Dao
import androidx.room.Query
import com.mkokic.data.database.dao.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface IngouDao : BaseDao<IngouDbModel> {

    @Query("SELECT * FROM INGOU_TABLE")
    fun getAllIngous(): Flow<List<IngouDbModel>>
}