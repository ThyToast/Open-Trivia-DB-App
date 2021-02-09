package com.example.opentriviadbdemoapp.data.api

import com.example.opentriviadbdemoapp.data.model.QuizCategoryCountResponse
import com.example.opentriviadbdemoapp.data.model.QuizCategoryListResponse
import com.example.opentriviadbdemoapp.data.model.QuizQuestionResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizApi {
    @GET("api.php")
    fun getQuizQuestion(
        @Query("amount") amount: Int,
        @Query("difficulty") difficulty: String,
        @Query("category") category: Int
    ): Call<QuizQuestionResponse>

    @GET("api_category.php")
    fun getQuizCategory(): Call<QuizCategoryListResponse>

    @GET("api_count.php")
    fun getQuizCount(
        @Query("category") category: Int
    ): Call<QuizCategoryCountResponse>
}