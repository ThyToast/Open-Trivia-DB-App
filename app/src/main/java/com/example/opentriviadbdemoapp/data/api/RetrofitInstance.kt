package com.example.opentriviadbdemoapp.data.api

import com.example.opentriviadbdemoapp.utils.Constant.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {
    private val interceptor: HttpLoggingInterceptor =
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(20, TimeUnit.SECONDS)


    private val adapterFactory =
        retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory.create()

    private fun createRetrofit(): Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient.build())
        .addCallAdapterFactory(adapterFactory)
        .build()

    fun createApi(): QuizApi {
        return createRetrofit().create(QuizApi::class.java)
    }
}