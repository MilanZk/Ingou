package com.mkokic.ingou.ingou.intent

import com.mkokic.domain.Ingou
import com.mkokic.ingou.mvibase.MviViewState

data class IngouViewState (
    val isLoading : Boolean,
    val ingous: List<Ingou>,
    val error: Throwable?
): MviViewState{
    companion object  {
        fun idle () : IngouViewState = IngouViewState (
            isLoading = false,
            ingous = arrayListOf(),
            error = null
        )
    }

}