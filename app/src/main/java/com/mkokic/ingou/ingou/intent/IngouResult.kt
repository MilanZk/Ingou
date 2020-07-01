package com.mkokic.ingou.ingou.intent

import com.mkokic.domain.Ingou
import com.mkokic.ingou.mvibase.MviResult

sealed class IngouResult : MviResult {

    sealed class LoadAllIngousResult : IngouResult() {
        object Loading : LoadAllIngousResult()
        data class Success(val ingous: List<Ingou>) : LoadAllIngousResult()
        data class Failure(val error: Throwable) : LoadAllIngousResult()
    }
}