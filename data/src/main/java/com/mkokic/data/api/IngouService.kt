package com.mkokic.data.api

import com.mkokic.domain.Ingou
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IngouService {

    companion object {
        const val BASE_URL = "http://192.168.1.20:7000/"
    }

    @GET("lingous")
    fun getIngous(): Observable<List<Ingou>>

    @POST("lingous")
    fun postIngou(@Body ingou: Ingou): Observable<Ingou>
}