package com.mkokic.ingou.add_ingou

import com.mkokic.domain.Ingou
import com.mkokic.ingou.mvibase.MviViewState

data class AddIngouViewState(
    val isProcessing: Boolean,
    val ingou: Ingou,
    val isSaveComplete: Boolean,
    val error: Throwable?
) : MviViewState {
    companion object {
        fun default() = AddIngouViewState(
            false,
            Ingou(idIngou = 0, name = "", description = ""),
            false,
            null
        )
    }
}