package com.example.opentriviadbdemoapp.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.opentriviadbdemoapp.data.api.RetrofitInstance
import com.example.opentriviadbdemoapp.data.model.QuizQuestion
import com.example.opentriviadbdemoapp.utils.NetworkState
import com.example.opentriviadbdemoapp.utils.State
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

private const val FIRST_ITEMS = 20

class QuizDataSource(
    private val quizApi: RetrofitInstance,
    private val category: Int,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, QuizQuestion>() {

    val networkState = MutableLiveData<NetworkState>()
    val initialLoad = MutableLiveData<NetworkState>()
    private var retryCompletable: Completable? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, QuizQuestion>
    ) {
        val items = params.requestedLoadSize
        val request = quizApi.createApi().getQuizQuestion(items, category)

        try {
            compositeDisposable.add(
                request
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            callback.onResult(it.responseResult, null, FIRST_ITEMS)
                            networkState.postValue(NetworkState(State.DONE))
                            initialLoad.postValue(NetworkState(State.DONE))

                        },
                        {
                            val error = NetworkState.error("Unable to load quiz")
                            networkState.postValue(error)
                            initialLoad.postValue(error)
                        })
            )
        } catch (exception: IOException) {
            setRetry { loadInitial(params, callback) }
            val error = NetworkState.error(exception.message ?: "IOException Error")
            networkState.postValue(error)
            initialLoad.postValue(error)

        } catch (exception: HttpException) {
            val error = NetworkState.error(exception.message ?: "HttpException Error")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, QuizQuestion>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, QuizQuestion>) {
        networkState.postValue(NetworkState(State.LOADING))

        try {
            compositeDisposable.add(
                quizApi
                    .createApi().getQuizQuestion(params.key, category)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        callback.onResult(it.responseResult, params.key + FIRST_ITEMS)
                        networkState.postValue(NetworkState(State.DONE))
                    }, {
                        val error = NetworkState.error("Unable to load quiz")
                        networkState.postValue(error)
                        initialLoad.postValue(error)
                    })
            )

        } catch (exception: IOException) {
            setRetry { loadAfter(params, callback) }

            val error = NetworkState.error(exception.message ?: "IOException Error")
            networkState.postValue(error)
            initialLoad.postValue(error)

        } catch (exception: HttpException) {

            val error = NetworkState.error(exception.message ?: "HttpException Error")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(
                retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }
}
