package com.mkokic.ingou.ingou

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mkokic.ingou.R
import com.mkokic.ingou.ingou.intent.IngouIntent
import com.mkokic.ingou.ingou.intent.IngouIntent.*
import com.mkokic.ingou.ingou.intent.IngouResult
import com.mkokic.ingou.ingou.intent.IngouViewState
import com.mkokic.ingou.mvibase.MviView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_ingou_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class IngouListFragment : Fragment(), MviView<IngouIntent, IngouViewState> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val ingouViewModel: IngouViewModel by sharedViewModel()
    private val disposables = CompositeDisposable()

    private val loadAllIntentPublisher =
        PublishSubject.create<LoadAllIngouIntent>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_ingou_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        bind()
    }

    private fun bind() {
        disposables.add(ingouViewModel.states().subscribe(this::render))
        ingouViewModel.processIntents(intents())
    }

    override fun intents(): Observable<IngouIntent> {
        return Observable.merge(
            loadIntent(),
            loadAllIntentPublisher
        )
    }

    private fun loadIntent(): Observable<LoadAllIngouIntent> {
        return Observable.just(LoadAllIngouIntent)
    }

    override fun render(state: IngouViewState) {
        //  progressBar.visible = state.isLoading

        if (state.ingous.isNotEmpty()) {
            testtv.text = state.ingous[0].name
        }else {
            testtv.text = "empty"

        }
        /*  if (state.ingous.isEmpty()) {
              creaturesRecyclerView.visible = false
              emptyState.visible = true
          } else {
              creaturesRecyclerView.visible = true
              emptyState.visible = false
              adapter.updateCreatures(state.creatures)
          }

          if (state.error != null) {
              Toast.makeText(this, "Error loading", Toast.LENGTH_LONG).show()
          }*/
    }

    override fun onStop() {
        super.onStop()
        disposables.dispose()
    }
}