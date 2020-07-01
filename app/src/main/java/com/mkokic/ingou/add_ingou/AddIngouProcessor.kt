package com.mkokic.ingou.add_ingou

import com.mkokic.domain.Ingou
import com.mkokic.domain.ingou.IngouRepository
import com.mkokic.ingou.add_ingou.AddIngouAction.*
import com.mkokic.ingou.add_ingou.AddIngouResult.*
import com.mkokic.ingou.executor.PostExecutionThread
import com.mkokic.ingou.executor.ThreadExecutor
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.schedulers.Schedulers

class AddIngouProcessor(
    private val ingouRepository: IngouRepository,
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) {

    private val nameProcessor =
        ObservableTransformer<IngouTextAction, IngouTextResult> { actions ->
            actions.map { IngouTextResult.Success(it.ingouText) }
                .cast(IngouTextResult::class.java)
                .onErrorReturn(IngouTextResult::Failure)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .startWith(IngouTextResult.Processing)
        }

    private val descriptionProcessor =
        ObservableTransformer<IngouDescriptionAction, IngouDescriptionResult> { actions ->
            actions.map { IngouDescriptionResult.Success(it.ingouDescription) }
                .cast(IngouDescriptionResult::class.java)
                .onErrorReturn(IngouDescriptionResult::Failure)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .startWith(IngouDescriptionResult.Processing)
        }

    private val saveProcessor =
        ObservableTransformer<SaveIngouAction, SaveIngouResult> { actions ->
            actions.map { action ->
                val ingou = Ingou(0, action.ingouText, action.description)
                ingouRepository.insertIngou(ingou)
            }
                .cast(SaveIngouResult::class.java)
                .onErrorReturn(SaveIngouResult::Failure)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .startWith(SaveIngouResult.Processing)
        }

    internal var actionProcessor =
        ObservableTransformer<AddIngouAction, AddIngouResult> { actions ->
            actions.publish { shared ->
                Observable.merge(
                    shared.ofType(IngouTextAction::class.java).compose(nameProcessor),
                    shared.ofType(IngouDescriptionAction::class.java).compose(descriptionProcessor),
                    shared.ofType(SaveIngouAction::class.java).compose(saveProcessor),
                    shared.filter { v ->
                        v !is AddIngouAction
                                && v !is IngouTextAction
                                && v !is IngouDescriptionAction
                                && v !is SaveIngouAction
                    }.flatMap {
                        Observable.error<AddIngouResult>(IllegalAccessException("Unknown action type &it"))
                    })
            }
        }
}