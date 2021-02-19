package com.example.opentriviadbdemoapp.data.api

import com.example.opentriviadbdemoapp.data.model.QuizCategoryCountResponse
import com.example.opentriviadbdemoapp.data.model.QuizCategoryListResponse
import com.example.opentriviadbdemoapp.data.model.QuizQuestionResponse
import com.example.opentriviadbdemoapp.data.model.QuizToken
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizApi {
    @GET("api.php")
    fun getQuizQuestion(
        @Query("amount") amount: Int,
        @Query("category") category: Int? = null,
        @Query("token") token: String? = null
    ): Single<QuizQuestionResponse>

    @GET("api_category.php")
    fun getQuizCategory(): Single<QuizCategoryListResponse>

    @GET("api_count.php")
    fun getQuizCount(
        @Query("category") category: Int
    ): Single<QuizCategoryCountResponse>

    @GET("api_token.php?command=request")
    fun getToken(): Single<QuizToken>
}