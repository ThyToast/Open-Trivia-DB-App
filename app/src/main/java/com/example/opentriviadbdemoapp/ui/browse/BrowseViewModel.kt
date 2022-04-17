package com.example.opentriviadbdemoapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opentriviadbdemoapp.data.model.QuizCategoryListResponse
import com.example.opentriviadbdemoapp.data.repository.QuizRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class BrowseViewModel(private val repository: QuizRepository) : ViewModel() {

    private var viewModelDisposable: CompositeDisposable = CompositeDisposable()
    val quizCategoryResponse: MutableLiveData<QuizCategoryListResponse> = MutableLiveData()

    fun getCategory() {
        viewModelDisposable.add(
            repository.getCategory()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        //onSuccess
                        quizCategoryResponse.postValue(it)
                        Log.d("getCategory", "Success")
                    }, {
                        Log.d("getCategory", "Failure")
                    }
                )
        )
    }


    override fun onCleared() {
        super.onCleared()
        //stops observables
        viewModelDisposable.dispose()
        Log.d("onCleared", "cleared")
    }
}