package com.example.opentriviadbdemoapp.ui.catalog

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opentriviadbdemoapp.data.model.QuizCategoryComposite
import com.example.opentriviadbdemoapp.data.model.QuizCategoryList
import com.example.opentriviadbdemoapp.data.repository.QuizRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CatalogViewModel(private val repository: QuizRepository) : ViewModel() {

    private var viewModelDisposable: CompositeDisposable = CompositeDisposable()

    val quizCategoryComposite: MutableLiveData<List<QuizCategoryComposite>> =
        MutableLiveData()

    fun getCategoryCount() {
        repository.getCategory().toObservable().subscribeOn(Schedulers.io())
            .flatMap { quizCategoryList -> Observable.just(quizCategoryList.category) }
            .subscribe({
                //calls getcount and inserts categoryList
                getCount(it)
            },
                {
                    Log.d("getCategory", "onFailure")
                }
            )
    }

    private fun getCount(item: MutableList<QuizCategoryList>) {

        val idList = item.map {
            it.categoryId
        }

        Observable.fromIterable(idList)
            .subscribeOn(Schedulers.io())
            .flatMap { result ->
                repository.getCount(result).toObservable()
            }
            .toList()
            .subscribe({ categoryNameList ->

                val categoryById: Map<Int, QuizCategoryList> =
                    item.associateBy { it.categoryId }

                val merge = categoryNameList.filter {
                    categoryById[it.categoryId] != null

                }.map { categoryCount ->
                    QuizCategoryComposite(
                        categoryById[categoryCount.categoryId]!!.categoryName,
                        categoryCount.categoryId,
                        categoryCount.categoryCount
                    )
                }
                quizCategoryComposite.postValue(merge.sortedBy {
                    it.categoryId
                })
            },
                {
                    Log.d("getCategoryCount", "onFailure")
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