package com.mkokic.ingou.add_ingou

import com.mkokic.ingou.mvibase.MviIntent

sealed class AddIngouIntent : MviIntent {

    data class IngouTextIntent(val ingouText: String) : AddIngouIntent()
    data class IngouDescriptionIntent(val ingouDescription: String) : AddIngouIntent()
    data class SaveIngouIntent(val ingouText: String, val description: String) : AddIngouIntent()

}