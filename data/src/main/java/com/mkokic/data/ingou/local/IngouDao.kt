package com.mkokic.data.ingou.local

import androidx.room.Dao
import androidx.room.Query
import com.mkokic.data.database.dao.BaseDao
import io.reactivex.Observable

@Dao
interface IngouDao : BaseDao<IngouDbModel> {

    @Query("SELECT * FROM INGOU_TABLE")
    fun getAllIngous(): Observable<List<IngouDbModel>>
}