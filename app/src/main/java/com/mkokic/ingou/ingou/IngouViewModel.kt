package com.mkokic.ingou.ingou

import androidx.lifecycle.ViewModel
import com.mkokic.ingou.ingou.intent.*
import com.mkokic.ingou.ingou.intent.IngouIntent.LoadAllIngouIntent
import com.mkokic.ingou.mvibase.MviViewModel
import com.mkokic.ingou.util.notOfType
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject


class IngouViewModel(
    private val actionProcessorHolder: IngouProcessor
) : ViewModel(), MviViewModel<IngouIntent, IngouViewState> {


    private val intentsSubject: PublishSubject<IngouIntent> = PublishSubject.create()

    private val stateObservable: Observable<IngouViewState> = compose()

    private val intentFilter: ObservableTransformer<IngouIntent, IngouIntent>
        get() = ObservableTransformer { intents ->
            intents.publish { shared ->
                Observable.merge(
                    shared.ofType(LoadAllIngouIntent::class.java).take(1),
                    shared.notOfType(LoadAllIngouIntent::class.java).take(1)
                )
            }
        }

    private fun compose(): Observable<IngouViewState> {
        return intentsSubject
            .compose(intentFilter)
            .map(this::actionFromIntent)
            .compose(actionProcessorHolder.actionProcessor)
            .scan(IngouViewState.idle(), reducer)
            .distinctUntilChanged()
            .replay(1)
            .autoConnect(0)
    }

    private fun actionFromIntent(intent: IngouIntent): IngouAction {
        return when (intent) {
            is LoadAllIngouIntent -> IngouAction.LoadAllIngouAction
        }
    }

    override fun processIntents(intents: Observable<IngouIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): Observable<IngouViewState> = stateObservable

    companion object {
        private val reducer = BiFunction { previousState: IngouViewState, result: IngouResult ->
            when (result) {
                is IngouResult.LoadAllIngousResult -> when (result) {
                    is IngouResult.LoadAllIngousResult.Success -> {
                        previousState.copy(isLoading = false, ingous = result.ingous)
                    }
                    is IngouResult.LoadAllIngousResult.Failure -> previousState.copy(
                        isLoading = false,
                        error = result.error
                    )
                    is IngouResult.LoadAllIngousResult.Loading -> previousState.copy(isLoading = true)
                }
            }
        }
    }


}