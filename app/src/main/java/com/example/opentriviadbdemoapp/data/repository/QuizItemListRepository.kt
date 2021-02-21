package com.example.opentriviadbdemoapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.opentriviadbdemoapp.data.api.RetrofitInstance
import com.example.opentriviadbdemoapp.data.model.QuizQuestion
import com.example.opentriviadbdemoapp.utils.NetworkState
import io.reactivex.rxjava3.disposables.CompositeDisposable

class QuizItemListRepository(private val retrofitInstance: RetrofitInstance) {

    lateinit var quizPagedList: LiveData<PagedList<QuizQuestion>>
    lateinit var quizDatasourceFactory: QuizDataSourceFactory

    fun getQuizItemList(
        compositeDisposable: CompositeDisposable,
        category: Int
    ): LiveData<PagedList<QuizQuestion>> {
        quizDatasourceFactory =
            QuizDataSourceFactory(retrofitInstance, category, compositeDisposable)

        val config = PagedList
            .Config.Builder()
            .setPageSize(20)
            .setInitialLoadSizeHint(25)
            .build()

        quizPagedList = LivePagedListBuilder(quizDatasourceFactory, config).build()
        return quizPagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<QuizDataSource, NetworkState>(
            quizDatasourceFactory.quizLiveDataSource, QuizDataSource::networkState
        )
    }
}