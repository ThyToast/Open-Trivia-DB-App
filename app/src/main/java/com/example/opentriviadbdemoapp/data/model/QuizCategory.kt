package com.example.opentriviadbdemoapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryListResponse(
    @Json(name = "trivia_categories")
    val category: List<CategoryList>
)

data class CategoryList(
    val id: Int,
    val name: String
)

