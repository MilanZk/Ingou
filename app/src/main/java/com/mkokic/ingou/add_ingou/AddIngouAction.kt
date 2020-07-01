package com.mkokic.ingou.add_ingou

sealed class AddIngouAction {

    data class IngouTextAction(val ingouText: String) : AddIngouAction()
    data class IngouDescriptionAction(val ingouDescription: String) : AddIngouAction()
    data class SaveIngouAction(val ingouText: String, val description: String) : AddIngouAction()
}