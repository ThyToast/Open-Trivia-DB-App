package com.example.opentriviadbdemoapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.example.opentriviadbdemoapp.data.api.QuizApi
import com.example.opentriviadbdemoapp.data.model.QuizQuestion
import com.example.opentriviadbdemoapp.data.model.Result
import io.reactivex.rxjava3.disposables.CompositeDisposable

class QuizItemListRepository(private val quizApi: QuizApi) {

    lateinit var quizItemList: LiveData<List<QuizQuestion>>
    lateinit var quizDatasourceFactory: QuizDataSourceFactory

    fun getQuizItemList(
        compositeDisposable: CompositeDisposable,
        category: Int
    ): com.example.opentriviadbdemoapp.data.model.Result<QuizQuestion> {
        quizDatasourceFactory = QuizDataSourceFactory(quizApi, category, compositeDisposable)

        val config = PagedList
            .Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(25)
            .build()

            return Result(
                pagedList = quizDatasourceFactory.toLiveData(config),
                networkState = quizDatasourceFactory.quizLiveDataSource.switchMap {
                    it.networkState
                },
                retry = {},
                refresh = { quizDatasourceFactory.quizLiveDataSource.value?.invalidate() },
                refreshState = quizDatasourceFactory.quizLiveDataSource.switchMap {
                    it.initialLoad
                }


            )


    }
}