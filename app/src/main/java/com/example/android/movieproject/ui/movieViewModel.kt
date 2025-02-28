package com.example.android.movieproject.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.movieproject.MyApplication
import kotlinx.coroutines.launch

class movieViewModel : ViewModel() {
    val MovieApiService = MyApplication().appContainer.movieApiService
    lateinit var _movies : String

    fun getMoviesFromApi() :String{
        viewModelScope.launch {
            _movies = getMovies()

        }
        return _movies
    }

    suspend fun getMovies() : String {
        return  MovieApiService.getPopularMovies()
    }






}