package com.mkokic.data.ingou

import com.mkokic.data.database.IngouDatabase
import com.mkokic.domain.Ingou
import com.mkokic.domain.ingou.IngouRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class IngouDataRepository(
    val ingouDatabase: IngouDatabase,
    val ingouMapper: IngouMapper
) : IngouRepository {

    override fun getAllIngous(): Flow<List<Ingou>> {
        return ingouDatabase.ingouDao().getAllIngous().map { ingouList ->
            ingouList.map {
                ingouMapper.mapFromDatabaseModel(it)
            }
        }
    }
}