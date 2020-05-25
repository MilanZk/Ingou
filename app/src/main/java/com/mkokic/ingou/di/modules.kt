package com.mkokic.ingou.di

import androidx.room.Room
import com.mkokic.data.database.IngouDatabase
import com.mkokic.data.ingou.IngouDataRepository
import com.mkokic.data.ingou.IngouMapper
import com.mkokic.domain.ingou.IngouRepository

import org.koin.dsl.module

val applicationModule = module(override = true) {
    Room.databaseBuilder(
        androidContext(),
        IngouDatabase::class.java, "ingou.sqlite"
    )
        .build()
}

val ingouModule = module(override = true) {
    factory { IngouMapper() }
    factory<IngouRepository> { IngouDataRepository(get(), get()) }
}