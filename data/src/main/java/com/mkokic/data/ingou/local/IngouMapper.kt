package com.mkokic.data.ingou.local

import com.mkokic.domain.Ingou

class IngouMapper() {

    fun mapToDatabaseModel(ingou: Ingou): IngouDbModel {
        return IngouDbModel(
            ingou.idIngou,
            ingou.name,
            ingou.description
        )
    }

    fun mapFromDatabaseModel(ingouDbModel: IngouDbModel): Ingou {
        return Ingou(ingouDbModel.idIngou, ingouDbModel.name, ingouDbModel.description)
    }
}