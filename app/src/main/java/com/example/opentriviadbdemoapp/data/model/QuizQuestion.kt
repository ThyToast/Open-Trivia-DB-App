package com.example.opentriviadbdemoapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuizQuestionResponse(
    @Json(name = "response_code")
    val responseCode: Int,
    @Json(name = "results")
    val responseResult: List<QuizQuestion>
)

@JsonClass(generateAdapter = true)
data class QuizQuestion(
    @Json(name = "category")
    val quizCategory: String,
    @Json(name = "type")
    val quizType: String,
    @Json(name = "difficulty")
    val quizDifficulty: String,
    @Json(name = "question")
    val quizQuestion: String,
    @Json(name = "correct_answer")
    val quizCorrectAnswer: String,
    @Json(name = "incorrect_answers")
    val quizWrongAnswer: List<String>,

    val viewType:Int = 0
)