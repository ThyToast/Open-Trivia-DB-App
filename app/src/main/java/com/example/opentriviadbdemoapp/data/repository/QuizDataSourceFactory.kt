package com.example.opentriviadbdemoapp.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.opentriviadbdemoapp.data.api.RetrofitInstance
import com.example.opentriviadbdemoapp.data.model.QuizQuestion
import io.reactivex.rxjava3.disposables.CompositeDisposable

class QuizDataSourceFactory(
    private val retrofitInstance: RetrofitInstance,
    private val category: Int,
    private val compositeDisposable: CompositeDisposable
) :
    DataSource.Factory<Int, QuizQuestion>() {

    val quizLiveDataSource = MutableLiveData<QuizDataSource>()

    override fun create(): DataSource<Int, QuizQuestion> {
        val quizDataSource = QuizDataSource(retrofitInstance, category, compositeDisposable)
        quizLiveDataSource.postValue(quizDataSource)
        return quizDataSource
    }


}