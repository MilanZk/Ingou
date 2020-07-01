package com.mkokic.ingou.ingou.intent

import com.mkokic.domain.Ingou
import com.mkokic.ingou.mvibase.MviViewState

sealed class IngouState: MviViewState {

    object Loading : IngouState()
    data class Success(val data: List<Ingou>) : IngouState()
    object Failure : IngouState()
}