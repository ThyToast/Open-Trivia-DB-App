package com.example.opentriviadbdemoapp.ui.quiz

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opentriviadbdemoapp.data.model.QuizCategoryListResponse
import com.example.opentriviadbdemoapp.data.model.QuizQuestionResponse
import com.example.opentriviadbdemoapp.data.repository.QuizRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class QuizViewModel(private val repository: QuizRepository) : ViewModel() {

    private var viewModelDisposable: CompositeDisposable = CompositeDisposable()

    val quizQuestionResponse: MutableLiveData<QuizQuestionResponse> = MutableLiveData()
    val quizCategoryResponse: MutableLiveData<QuizCategoryListResponse> = MutableLiveData()
    val quizResult: MutableLiveData<Int> = MutableLiveData()

    fun getQuiz(amount: Int, category: Int? = null) {
        viewModelDisposable.add(repository.getQuiz(amount, category)
            .toObservable()
            .subscribeOn(Schedulers.io())
            .subscribe(
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