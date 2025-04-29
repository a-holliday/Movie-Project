package com.example.android.movieproject.ui

import android.util.Log
import androidx.lifecycle.*
import com.example.android.movieproject.MyApplication
import com.example.android.movieproject.data.Movie
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewModel : ViewModel() {
    private val movieApiService = MyApplication.appContainer.movieApiService

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies  // Expose immutable LiveData
    var movie : Movie? = null// Placeholder for a single movie

    private fun fetchPopularMovies() {
        viewModelScope.launch {
                val jsonString = withContext(Dispatchers.IO) {
                    movieApiService.getPopularMovies() // Runs on IO thread
                }
                _movies.postValue(parseMovies(jsonString)) // Updates UI on the main thread

        }
    }
     fun searchMovies(query: String) {
        viewModelScope.launch {
            val jsonString = withContext(Dispatchers.IO) {
                movieApiService.searchMovies(query) // Runs on IO thread
            }
            _movies.postValue(parseMovies(jsonString)) // Updates UI on the main thread
        }
    }

    private fun parseMovies(jsonString: String): List<Movie> {
        return try {
            val jsonElement = JsonParser.parseString(jsonString)
            val jsonObject = jsonElement.asJsonObject

            // Find the first array in the JSON object
            val jsonArray = jsonObject.entrySet().firstOrNull { it.value.isJsonArray }?.value?.asJsonArray
            val type = object : TypeToken<List<Movie>>() {}.type

            // Parse the JSON array to a List<Movie>; otherwise return an empty list.
            jsonArray?.let {
                Gson().fromJson(it, type)
            } ?: emptyList()
        } catch (e: Exception) {
            Log.e("MovieViewModel", "Error parsing JSON", e)
            emptyList()
        }
    }

    fun onPopularClicked() {
        fetchPopularMovies()
    }

    fun getMovie(movieId : Int?) {
        movie = movies.value?.firstOrNull { it.id == movieId }


    }

}