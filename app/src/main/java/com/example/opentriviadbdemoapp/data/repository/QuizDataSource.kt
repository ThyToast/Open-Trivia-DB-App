package com.example.opentriviadbdemoapp.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.example.opentriviadbdemoapp.data.api.QuizApi
import com.example.opentriviadbdemoapp.data.model.QuizQuestion
import com.example.opentriviadbdemoapp.utils.NetworkState
import com.example.opentriviadbdemoapp.utils.State
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

private const val FIRST_ITEMS = 20

class QuizDataSource(
    private val quizApi: QuizApi,
    private val category: Int,
    private val compositeDisposable: CompositeDisposable
) : ItemKeyedDataSource<Int, QuizQuestion>() {

    val networkState = MutableLiveData<NetworkState>()
    val initialLoad = MutableLiveData<NetworkState>()
    private var retry: (() -> Any)? = null


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<QuizQuestion>
    ) {
        val position = params.requestedInitialKey ?: FIRST_ITEMS
        val request = quizApi.getQuizQuestion(category, position)

        try {
            request
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.responseResult)
                    },
                    {
                        val error = NetworkState.error("Unable to load quiz")
                        networkState.postValue(error)
                        initialLoad.postValue(error)
                    })

        } catch (exception: IOException) {
            retry = {
                loadInitial(params, callback)
            }
            val error = NetworkState.error(exception.message ?: "IOException Error")
            networkState.postValue(error)
            initialLoad.postValue(error)

        } catch (exception: HttpException) {
            val error = NetworkState.error(exception.message ?: "HttpException Error")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<QuizQuestion>) {
        networkState.postValue(NetworkState(State.LOADING))

        try {
            quizApi
                .getQuizQuestion(params.key, category)
                .subscribeOn(Schedulers.io())
                .map { quizQuestion ->
                    if (quizQuestion.responseResult.size >= params.key) {
                        quizApi.getQuizQuestion(category, params.key + FIRST_ITEMS)
                            .subscribeOn(Schedulers.io())
                            .subscribe({
                                callback.onResult(it.responseResult)
                            }, {
                                val error = NetworkState.error("Unable to load quiz")
                                networkState.postValue(error)
                                initialLoad.postValue(error)
                            })
                    }
                }
        } catch (exception: IOException) {
            retry = {
                loadAfter(params, callback)
            }

            val error = NetworkState.error(exception.message ?: "IOException Error")
            networkState.postValue(error)
            initialLoad.postValue(error)

        } catch (exception: HttpException) {

            val error = NetworkState.error(exception.message ?: "HttpException Error")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }
    }


    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<QuizQuestion>) {

    }

    override fun getKey(item: QuizQuestion): Int {
        return FIRST_ITEMS
    }

}