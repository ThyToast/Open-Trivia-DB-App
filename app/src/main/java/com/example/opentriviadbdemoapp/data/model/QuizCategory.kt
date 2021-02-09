package com.example.opentriviadbdemoapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuizCategoryListResponse(
    @Json(name = "trivia_categories")
    val category: List<QuizCategoryList>
)

@JsonClass(generateAdapter = true)
data class QuizCategoryList(
    val id: Int,
    val name: String
)

