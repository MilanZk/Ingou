package com.mkokic.ingou.ingou.intent

import com.mkokic.ingou.mvibase.MviIntent

sealed class IngouIntent : MviIntent {

   object LoadAllIngouIntent : IngouIntent()
}

