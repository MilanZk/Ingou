package com.mkokic.ingou.di

import androidx.room.Room
import com.mkokic.data.api.IngouServiceFactory
import com.mkokic.data.database.IngouDatabase
import com.mkokic.data.ingou.local.IngouDataRepository
import com.mkokic.data.ingou.local.IngouMapper
import com.mkokic.domain.ingou.IngouRepository
import com.mkokic.ingou.BuildConfig
import com.mkokic.ingou.add_ingou.AddIngouProcessor
import com.mkokic.ingou.add_ingou.AddIngouViewModel
import com.mkokic.ingou.executor.JobExecutor
import com.mkokic.ingou.executor.PostExecutionThread
import com.mkokic.ingou.executor.ThreadExecutor
import com.mkokic.ingou.executor.UiThread
import com.mkokic.ingou.ingou.IngouProcessor
import com.mkokic.ingou.ingou.IngouViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module

val applicationModule = module(override = true) {

    single {
        Room.databaseBuilder(
            androidContext(),
            IngouDatabase::class.java, "ingou.sqlite"
        )
            .build()
    }

    single { JobExecutor() as ThreadExecutor }
    single { UiThread() as PostExecutionThread }

    factory { IngouServiceFactory.makeBufferooService(androidContext(), BuildConfig.DEBUG) }

}

val ingouModule = module(override = true) {
    factory { IngouMapper() }
    factory { get<IngouDatabase>().ingouDao() }
    factory<IngouRepository> { IngouDataRepository(get(), get(), get()) }
    factory { IngouProcessor(get(), get(), get()) }
    factory { AddIngouProcessor(get(), get(), get()) }
    viewModel { IngouViewModel(get()) }
    viewModel { AddIngouViewModel(get()) }

}