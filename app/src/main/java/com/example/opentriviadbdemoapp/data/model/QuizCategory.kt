package com.example.opentriviadbdemoapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuizCategoryListResponse(
    @Json(name = "trivia_categories")
    val category: MutableList<QuizCategoryList>
)

@JsonClass(generateAdapter = true)
data class QuizCategoryList(
    @Json(name = "id")
    val categoryId: Int,
    @Json(name = "name")
    val categoryName: String
)

