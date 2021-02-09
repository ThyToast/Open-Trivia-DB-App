package com.example.opentriviadbdemoapp.data.repository

import com.example.opentriviadbdemoapp.data.api.RetrofitInstance
import com.example.opentriviadbdemoapp.data.model.QuizCategoryCountResponse
import com.example.opentriviadbdemoapp.data.model.QuizCategoryListResponse
import com.example.opentriviadbdemoapp.data.model.QuizQuestionResponse
import io.reactivex.rxjava3.core.Single

class QuizRepository(private val retrofitInstance: RetrofitInstance) {
    fun getCategory(): Single<QuizCategoryListResponse> {
        return retrofitInstance.createApi().getQuizCategory()
    }

    fun getCount(category: Int): Single<QuizCategoryCountResponse> {
        return retrofitInstance.createApi().getQuizCount(category)
    }

    fun getQuiz(amount: Int, category: Int): Single<QuizQuestionResponse> {
        return retrofitInstance.createApi().getQuizQuestion(amount, category)
    }
}