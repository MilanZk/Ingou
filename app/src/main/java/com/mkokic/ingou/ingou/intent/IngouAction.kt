package com.mkokic.ingou.ingou.intent

import com.mkokic.ingou.mvibase.MviAction

sealed class IngouAction: MviAction {

    object LoadAllIngouAction : IngouAction()
}