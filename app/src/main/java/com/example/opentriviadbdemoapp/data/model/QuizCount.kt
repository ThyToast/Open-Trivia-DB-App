package com.example.opentriviadbdemoapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryCountResponse(
    @Json(name = "category_id")
    val categoryId: Int,
    @Json(name = "category_question_count")
    val questionCount: List<CategoryCount>
)

data class CategoryCount(
    @Json(name = "total_question_count")
    val totalQuestion: Int,
    @Json(name = "total_easy_question_count")
    val easyQuestion: Int,
    @Json(name = "total_medium_question_count")
    val mediumQuestion: Int,
    @Json(name = "total_hard_question_count")
    val hardQuestion: Int,
)