package com.mkokic.ingou.add_ingou

import androidx.lifecycle.ViewModel
import com.mkokic.domain.Ingou
import com.mkokic.ingou.add_ingou.AddIngouAction.*
import com.mkokic.ingou.add_ingou.AddIngouIntent.*
import com.mkokic.ingou.add_ingou.AddIngouResult.*
import com.mkokic.ingou.add_ingou.AddIngouResult.IngouTextResult.Failure
import com.mkokic.ingou.add_ingou.AddIngouResult.IngouTextResult.Processing
import com.mkokic.ingou.mvibase.MviViewModel
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject

class AddIngouViewModel(
    private val actionProcessorHolder: AddIngouProcessor
) : ViewModel(), MviViewModel<AddIngouIntent, AddIngouViewState> {

    private val intentsSubject: PublishSubject<AddIngouIntent> = PublishSubject.create()

    private val stateObservable: Observable<AddIngouViewState> = compose()


    private fun compose(): Observable<AddIngouViewState> {
        return intentsSubject
            .map(this::actionFromIntent)
            .compose(actionProcessorHolder.actionProcessor)
            .scan(AddIngouViewState.default(), reducer)
            .distinctUntilChanged()
            .replay(1)
            .autoConnect(0)
    }

    private fun actionFromIntent(intent: AddIngouIntent): AddIngouAction {
        return when (intent) {
            is IngouTextIntent -> IngouTextAction(intent.ingouText)
            is IngouDescriptionIntent -> IngouDescriptionAction(intent.ingouDescription)
            is SaveIngouIntent -> SaveIngouAction(intent.ingouText, intent.description)
        }
    }

    override fun processIntents(intents: Observable<AddIngouIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): Observable<AddIngouViewState> = stateObservable

    companion object {

        private val reducer =
            BiFunction { previousState: AddIngouViewState, result: AddIngouResult ->
                when (result) {
                    is IngouTextResult -> reduceName(previousState, result)
                    is IngouDescriptionResult -> reduceDescription(previousState, result)
                    is SaveIngouResult -> reduceSave(previousState, result)
                }
            }

        private fun reduceName(
            previousState: AddIngouViewState,
            result: IngouTextResult
        ): AddIngouViewState = when (result) {
            is IngouTextResult.Success -> {
                previousState.copy(
                    isProcessing = false,
                    ingou = Ingou(
                        previousState.ingou.idIngou,
                        result.ingouText,
                        previousState.ingou.description
                    )
                )
            }
            is Failure -> {
                previousState.copy(
                    isProcessing = false,
                    error = result.error
                )
            }
            is Processing -> previousState.copy(isProcessing = true, error = null)
        }

        private fun reduceDescription(
            previousState: AddIngouViewState,
            result: IngouDescriptionResult
        ): AddIngouViewState = when (result) {
            is IngouDescriptionResult.Success -> {
                previousState.copy(
                    isProcessing = false,
                    ingou = Ingou(
                        previousState.ingou.idIngou,
                        result.ingouDescription,
                        previousState.ingou.description
                    )
                )
            }
            is IngouDescriptionResult.Failure -> {
                previousState.copy(
                    isProcessing = false,
                    error = result.error
                )
            }
            is IngouDescriptionResult.Processing -> previousState.copy(
                isProcessing = true,
                error = null
            )
        }

        private fun reduceSave(
            previousState: AddIngouViewState,
            result: SaveIngouResult
        ): AddIngouViewState = when (result) {
            is SaveIngouResult.Success -> {
                previousState.copy(
                    isSaveComplete = true,
                    isProcessing = false,
                    error = null
                )
            }
            is SaveIngouResult.Failure -> {
                previousState.copy(
                    isProcessing = false,
                    error = result.error
                )
            }
            is SaveIngouResult.Processing -> previousState.copy(isProcessing = true, error = null)
        }
    }
}

