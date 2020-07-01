package com.mkokic.ingou.add_ingou

import com.mkokic.ingou.mvibase.MviResult

sealed class AddIngouResult : MviResult {

    sealed class IngouTextResult : AddIngouResult() {
        object Processing : IngouTextResult()
        data class Success(val ingouText: String) : IngouTextResult()
        data class Failure(val error: Throwable) : IngouTextResult()
    }

    sealed class IngouDescriptionResult : AddIngouResult() {
        object Processing : IngouDescriptionResult()
        data class Success(val ingouDescription: String) : IngouDescriptionResult()
        data class Failure(val error: Throwable) : IngouDescriptionResult()
    }

    sealed class SaveIngouResult : AddIngouResult() {
        object Processing : SaveIngouResult()
        object Success : SaveIngouResult()
        data class Failure(val error: Throwable) : SaveIngouResult()
    }
}