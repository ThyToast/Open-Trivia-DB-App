package com.example.opentriviadbdemoapp.ui.browse

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.opentriviadbdemoapp.data.model.QuizQuestion
import com.example.opentriviadbdemoapp.data.repository.QuizItemListRepository
import com.example.opentriviadbdemoapp.utils.NetworkState
import io.reactivex.rxjava3.disposables.CompositeDisposable

class BrowseDataViewModel(private val repository: QuizItemListRepository) : ViewModel() {

    private val viewModelDisposable: CompositeDisposable = CompositeDisposable()

    val quizPagedList: LiveData<PagedList<QuizQuestion>> by lazy {
        repository.getQuizItemList(viewModelDisposable, 1)
    }

    fun getQuizItemList(category: Int): LiveData<PagedList<QuizQuestion>> {
        return repository.getQuizItemList(viewModelDisposable, category)
    }

    val networkState: LiveData<NetworkState> by lazy {
        repository.getNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelDisposable.dispose()
    }


}