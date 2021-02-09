package com.example.opentriviadbdemoapp.ui.browse

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opentriviadbdemoapp.data.model.QuizQuestionResponse
import com.example.opentriviadbdemoapp.data.repository.QuizRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class BrowseViewModel(private val repository: QuizRepository) : ViewModel() {

    private var viewModelDisposable: CompositeDisposable = CompositeDisposable()
    val quizQuestionResponse: MutableLiveData<QuizQuestionResponse> = MutableLiveData()

    fun getQuiz(amount: Int, category: Int) {
        viewModelDisposable.add(repository.getQuiz(amount, category).toObservable()
            .subscribeOn(Schedulers.io()).subscribe(
                {
                    //onSuccess
                    quizQuestionResponse.postValue(it)
                    Log.d("getQuiz", "Success")

                }, {
                    //onFailure
                    Log.d("getQuiz", "onFailure")

                }
            ))
    }


    override fun onCleared() {
        super.onCleared()
        //stops observables
        viewModelDisposable.dispose()
    }
}