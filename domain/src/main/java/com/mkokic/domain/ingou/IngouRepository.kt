package com.mkokic.domain.ingou

import com.mkokic.domain.Ingou
import kotlinx.coroutines.flow.Flow

interface IngouRepository {

    fun getAllIngous(): Flow<List<Ingou>>
/*
    fun insertIngou()

    fun updateIngou()

    fun deleteIngou()*/
}