package com.example.opentriviadbdemoapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuizToken(
    @Json(name = "response_code")
    val categoryId: String
)