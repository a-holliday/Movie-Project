package com.example.android.movieproject.network
import com.example.android.movieproject.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers

const val BASE_URL = "https://api.themoviedb.org/3/"
interface MovieApi {

        @Headers("Authorization: Bearer ${BuildConfig.MOVIE_TOKEN}")
        @GET("movie/popular")
        suspend fun getPopularMovies(): String




}

