package com.mkokic.ingou.ingou

import com.mkokic.domain.ingou.IngouRepository
import com.mkokic.ingou.executor.PostExecutionThread
import com.mkokic.ingou.executor.ThreadExecutor
import com.mkokic.ingou.ingou.intent.IngouAction
import com.mkokic.ingou.ingou.intent.IngouResult.LoadAllIngousResult
import com.mkokic.ingou.util.schedulers.BaseSchedulerProvider
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class IngouProcessor(
    private val ingouRepository: IngouRepository,
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) {

    private val loadAllIngouProcessor =
        ObservableTransformer<IngouAction.LoadAllIngouAction, LoadAllIngousResult> { actions ->
            actions.flatMap {
                ingouRepository.getAllIngous()
                    .map { ingous -> LoadAllIngousResult.Success(ingous) }
                    .cast(LoadAllIngousResult::class.java)
                    .onErrorReturn(LoadAllIngousResult::Failure)
                    .subscribeOn(Schedulers.from(threadExecutor))
                    .observeOn(postExecutionThread.scheduler)
                    .startWith(LoadAllIngousResult.Loading)
            }
        }

    internal var actionProcessor =
        ObservableTransformer<IngouAction, LoadAllIngousResult> { actions ->
            actions.publish { shared ->
                Observable.merge(
                    shared.ofType(IngouAction.LoadAllIngouAction::class.java)
                        .compose(loadAllIngouProcessor),
                    shared.filter { v ->
                        v !is IngouAction.LoadAllIngouAction
                    }.flatMap { w ->
                        Observable.error<LoadAllIngousResult>(
                            IllegalAccessException("Unknown Action type: $w")
                        )
                    })
            }
        }

}