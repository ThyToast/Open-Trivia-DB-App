package com.example.opentriviadbdemoapp.di

import com.example.opentriviadbdemoapp.data.api.RetrofitInstance
import com.example.opentriviadbdemoapp.data.repository.QuizItemListRepository
import com.example.opentriviadbdemoapp.data.repository.QuizRepository
import com.example.opentriviadbdemoapp.ui.browse.BrowseDataViewModel
import com.example.opentriviadbdemoapp.ui.catalog.CatalogViewModel
import com.example.opentriviadbdemoapp.ui.quiz.QuizViewModel
import com.example.opentriviadbdemoapp.ui.viewModel.BrowseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelModule: Module = module {
    viewModel { BrowseViewModel(get()) }
    viewModel { CatalogViewModel(get()) }
    viewModel { QuizViewModel(get()) }
    viewModel { BrowseDataViewModel(get()) }
    factory { QuizItemListRepository(get()) }
    factory { QuizRepository(get()) }
    single { RetrofitInstance() }
}