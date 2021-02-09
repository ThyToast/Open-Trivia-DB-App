package com.example.opentriviadbdemoapp.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuizQuestionResponse(
    val responseCode: Int,
    val responseResult: List<QuizQuestion>
)

data class QuizQuestion(
    val quizCategory: String,
    val quizType: String,
    val quizDifficulty: String,
    val quizQuestion: String,
    val quizCorrectAnswer: String,
    val quizWrongAnswer: List<String>
)