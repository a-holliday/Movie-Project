package com.example.android.movieproject.network
import com.example.android.movieproject.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

const val BASE_URL = "https://api.themoviedb.org/3/"
class MovieApiService {

    interface MovieApi{
        @Headers("Authorization: Bearer ${BuildConfig.MOVIE_TOKEN}")
        @GET("movie/popular")
        suspend fun getPopularMovies(): String
    }

    object MovieApiService{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val movieApi = retrofit.create(MovieApi::class.java)
    }


}

