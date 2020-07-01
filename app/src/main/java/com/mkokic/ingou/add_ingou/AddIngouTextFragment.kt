package com.mkokic.ingou.add_ingou

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mkokic.ingou.R
import com.mkokic.ingou.mvibase.MviView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_add_ingou_text.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AddIngouTextFragment : BottomSheetDialogFragment(),
    MviView<AddIngouIntent, AddIngouViewState> {

    private val addIngouViewModel: AddIngouViewModel by sharedViewModel()
    private val disposables = CompositeDisposable()

    private val saveIngouIntentPublisher =
        PublishSubject.create<AddIngouIntent.SaveIngouIntent>()
    private val ingouTextIngouTextPublisher =
        PublishSubject.create<AddIngouIntent.IngouTextIntent>()
    private val ingouDescriptionPublisher =
        PublishSubject.create<AddIngouIntent.IngouDescriptionIntent>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_add_ingou_text, container, false)
    }

    override fun onStart() {
        super.onStart()
        bind()
    }

    private fun bind() {
        disposables.add(addIngouViewModel.states().subscribe(this::render))
        addIngouViewModel.processIntents(intents())
        saveIngou()
    }

    override fun intents(): Observable<AddIngouIntent> {
        return Observable.merge(
            ingouDescriptionPublisher,
            ingouTextIngouTextPublisher,
            saveIntent()
        )
    }

    private fun saveIngou() {
        val ingouDescription = et_ingou_description.text.toString()
        val ingouText = et_ingou.text.toString()
        bt_save_ingou.setOnClickListener {
            saveIngouIntentPublisher.onNext(
                AddIngouIntent.SaveIngouIntent(
                    ingouText,
                    ingouDescription
                )
            )
        }
    }

    override fun render(state: AddIngouViewState) {
        if (state.isSaveComplete) {
            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
        }

        if (state.error != null) {
            Log.e("Error","", state.error)
            Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveIntent(): Observable<AddIngouIntent.SaveIngouIntent> {
        return saveIngouIntentPublisher
    }

    override fun onStop() {
        super.onStop()
        disposables.dispose()
    }
}