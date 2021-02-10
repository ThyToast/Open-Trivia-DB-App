package com.example.opentriviadbdemoapp.di

import com.example.opentriviadbdemoapp.data.api.RetrofitInstance
import com.example.opentriviadbdemoapp.data.repository.QuizRepository
import com.example.opentriviadbdemoapp.ui.viewModel.QuizViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelModule: Module = module {
    viewModel { QuizViewModel(get()) }
    factory { QuizRepository(get()) }
    single { RetrofitInstance() }
}