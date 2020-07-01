package com.mkokic.domain.ingou

import com.mkokic.domain.Ingou
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IngouRepository {

    fun getAllIngous(): Observable<List<Ingou>>

    fun insertIngou(ingou: Ingou): Observable<Ingou>

    /* fun updateIngou()

     fun deleteIngou()*/
}