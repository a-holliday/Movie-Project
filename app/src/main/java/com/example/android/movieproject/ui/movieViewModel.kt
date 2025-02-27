package com.example.android.movieproject.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.movieproject.data.Movie
import com.example.android.movieproject.network.MovieApiService
import kotlinx.coroutines.launch

class movieViewModel : ViewModel() {
    lateinit var _movies : String

    fun getMoviesFromApi() :String{
        viewModelScope.launch {
            _movies = getMovies()

        }
        return _movies
    }

    suspend fun getMovies() : String {
        return  MovieApiService.MovieApiService.movieApi.getPopularMovies()
    }






}