package com.mkokic.data.ingou.local

import com.mkokic.data.api.IngouService
import com.mkokic.data.database.IngouDatabase
import com.mkokic.domain.Ingou
import com.mkokic.domain.ingou.IngouRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

class IngouDataRepository(
    private val ingouDatabase: IngouDatabase,
    private val ingouService: IngouService,
    private val ingouMapper: IngouMapper
) : IngouRepository {

    private var saveSubject: PublishSubject<Ingou> = PublishSubject.create()

    override fun getAllIngous(): Observable<List<Ingou>> {
        return Observable.defer {
            ingouService.getIngous()
        }
    }

    private fun getCachedIngous(): Observable<List<Ingou>>? {
        return ingouDatabase.ingouDao().getAllIngous().map { ingouList ->
            ingouList.map {
                ingouMapper.mapFromDatabaseModel(it)
            }
        }
    }

    override fun insertIngou(ingou: Ingou): Observable<Ingou> {
        return Observable.defer {
            ingouService.postIngou(ingou).flatMap {
                ingouDatabase.ingouDao().insert(ingouMapper.mapToDatabaseModel(it))
                    .andThen(Observable.just(it))
            }
        }
    }
}