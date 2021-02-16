package com.example.opentriviadbdemoapp.ui.catalog

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opentriviadbdemoapp.data.model.QuizCategoryComposite
import com.example.opentriviadbdemoapp.data.model.QuizCategoryList
import com.example.opentriviadbdemoapp.data.model.QuizCategoryListResponse
import com.example.opentriviadbdemoapp.data.repository.QuizRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CatalogViewModel(private val repository: QuizRepository) : ViewModel() {

    private var viewModelDisposable: CompositeDisposable = CompositeDisposable()

    val quizCategoryComposite: MutableLiveData<List<QuizCategoryComposite>> =
        MutableLiveData()

    fun getCategoryCount() {
        var categoryList = QuizCategoryListResponse(mutableListOf())
        val idList = mutableListOf<Int>()

        viewModelDisposable.add(
            repository.getCategory().toObservable().flatMap { categoryListResponse ->
                categoryList = categoryListResponse

                for (i in categoryListResponse.category.indices) {
                    idList.add(categoryListResponse.category[i].categoryId)
                }

                Observable.fromIterable(idList.sorted()).flatMap { result ->
                    repository.getCount(result).toObservable()
                }.throttleLast(50, TimeUnit.MILLISECONDS)
            }
                .toList()
                .subscribeOn(Schedulers.io())
                .subscribe({ categoryNameList ->

                    val categoryById: Map<Int, QuizCategoryList> =
                        categoryList.category.associateBy { it.categoryId }

                    //creates a new composite class combining the mapped categoryList and categoryCount ID and category count
                    val merge = categoryNameList.filter {
                        categoryById[it.categoryId] != null
                    }.map { categoryCount ->
                        categoryById[categoryCount.categoryId]?.let {
                            QuizCategoryComposite(
                                it.categoryName,
                                categoryCount.categoryId,
                                categoryCount.categoryCount
                            )
                        }
                    }
                    quizCategoryComposite.postValue(merge.filterNotNull().sortedBy {
                        it.categoryId
                    })

                }, {
                    Log.d("getCategoryCount", "onFailure")
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        //stops observables
        viewModelDisposable.dispose()
        Log.d("onCleared", "cleared")
    }
}