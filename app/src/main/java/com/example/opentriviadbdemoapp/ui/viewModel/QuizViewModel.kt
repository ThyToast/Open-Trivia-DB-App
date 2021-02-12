package com.example.opentriviadbdemoapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opentriviadbdemoapp.data.model.QuizCategoryCountResponse
import com.example.opentriviadbdemoapp.data.model.QuizCategoryListResponse
import com.example.opentriviadbdemoapp.data.model.QuizQuestionResponse
import com.example.opentriviadbdemoapp.data.repository.QuizRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class QuizViewModel(private val repository: QuizRepository) : ViewModel() {

    private var viewModelDisposable: CompositeDisposable = CompositeDisposable()

    val quizQuestionResponse: MutableLiveData<QuizQuestionResponse> = MutableLiveData()
    val quizCategoryResponse: MutableLiveData<QuizCategoryListResponse> = MutableLiveData()
    val quizCategoryCountResponse: MutableLiveData<MutableList<QuizCategoryCountResponse>> =
        MutableLiveData()


    fun getQuiz(amount: Int, category: Int) {
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

    fun getCount(category: Int) {
        viewModelDisposable.add(repository.getCount(category)
            .toObservable().toList()
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    //onSuccess
                    quizCategoryCountResponse.postValue(it)
                    Log.d("getCount", "Success")

                }, {
                    //onFailure
                    Log.d("getCount", "onFailure")
                }
            )
        )
    }

    fun getCount2(category: Int) {
        repository.getCount(category)
            .toObservable().toList()
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    //onSuccess
                    quizCategoryCountResponse.postValue(it)
                    Log.d("getCount", "Success")

                }, {
                    //onFailure
                    Log.d("getCount", "onFailure")
                }
            )
    }


    override fun onCleared() {
        super.onCleared()
        //stops observables
        viewModelDisposable.dispose()
        Log.d("onCleared", "cleared")
    }
}