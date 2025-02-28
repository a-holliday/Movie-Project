package com.example.android.movieproject.di

import com.example.android.movieproject.network.BASE_URL
import com.example.android.movieproject.network.MovieApi
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class AppContainer {

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
    val movieApiService = retrofit.create(MovieApi::class.java)
}