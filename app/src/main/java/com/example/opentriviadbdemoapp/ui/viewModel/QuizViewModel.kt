package com.example.opentriviadbdemoapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opentriviadbdemoapp.data.model.*
import com.example.opentriviadbdemoapp.data.repository.QuizRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class QuizViewModel(private val repository: QuizRepository) : ViewModel() {

    private var viewModelDisposable: CompositeDisposable = CompositeDisposable()

    val quizQuestionResponse: MutableLiveData<QuizQuestionResponse> = MutableLiveData()
    val quizCategoryResponse: MutableLiveData<QuizCategoryListResponse> = MutableLiveData()
    val quizCategoryCountResponse: MutableLiveData<MutableList<QuizCategoryCountResponse>> =
        MutableLiveData()
    val quizCategoryComposite: MutableLiveData<List<QuizCategoryComposite>> =
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


    fun getCategoryCount() {
        val nameList = mutableListOf<String>()
        val idList = mutableListOf<Int>()
        var categoryList = QuizCategoryListResponse(mutableListOf())

        repository.getCategory().toObservable().flatMap { categoryListResponse ->
            categoryList = categoryListResponse

            for (i in categoryListResponse.category.indices) {
                idList.add(categoryListResponse.category[i].categoryId)
            }

            for (i in categoryListResponse.category.indices) {
                nameList.add(categoryListResponse.category[i].categoryName)
            }

            Observable.fromIterable(idList).flatMap { result ->
                repository.getCount(result).toObservable()
            }
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
    }

//    fun getCategoryCountNullable() {
//        val nameList = mutableListOf<String>()
//        val idList = mutableListOf<Int>()
//        var categoryList = QuizCategoryListResponse(mutableListOf())
//
//        repository.getCategory().toObservable().flatMap { categoryListResponse ->
//            categoryList = categoryListResponse
//
//            for (i in categoryListResponse.category.indices) {
//                idList.add(categoryListResponse.category[i].categoryId)
//            }
//
//            for (i in categoryListResponse.category.indices) {
//                nameList.add(categoryListResponse.category[i].categoryName)
//            }
//
//            Observable.fromIterable(idList).flatMap { result ->
//                repository.getCount(result).toObservable()
//            }
//        }
//            .toList()
//            .subscribeOn(Schedulers.io())
//            .subscribe({ categoryNameList ->
//
//                val categoryById: Map<Int, QuizCategoryList> =
//                    categoryList.category.associateBy { it.categoryId }
//
//
//                //creates a new composite class combining the mapped categoryList and categoryCount ID and category count
//                val merge = categoryNameList.filter {
//                    categoryById[it.categoryId] != null
//                }.map { categoryCount ->
//                    QuizCategoryComposite(
//                        categoryById[categoryCount.categoryId]!!.categoryName,
//                        categoryCount.categoryId,
//                        categoryCount.categoryCount
//                    )
//                }
//                quizCategoryComposite.postValue(merge.sortedBy {
//                    it.categoryId
//                })
//
//            }, {
//                Log.d("getCategoryCount", "onFailure")
//            })
//    }


    override fun onCleared() {
        super.onCleared()
        //stops observables
        viewModelDisposable.dispose()
        Log.d("onCleared", "cleared")
    }
}