package com.example.android.movieproject

import android.R.attr.tag
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android.movieproject.data.Movie
import com.example.android.movieproject.ui.MovieViewModel
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: MovieViewModel = viewModel()
            val navController = rememberNavController()

            MaterialTheme { // Apply your app's theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MovieAppNavHost(navController, viewModel)
                }
            }
        }
    }


    @Composable
    fun MovieAppNavHost(navController: NavHostController, viewModel: MovieViewModel) {
        NavHost(navController = navController, startDestination = "landing") {
            composable("landing") {
                MovieLandingScreen(
                    onPopularClick = {
                        navController.navigate("popular")
                        viewModel.onPopularClicked()
                    },
                    onSearchClick = { /* Do nothing for now */ }
                )
            }
            composable("popular") {
                PopularMovies(viewModel)
            }
        }
    }

    @Composable
    fun MovieLandingScreen(onPopularClick: () -> Unit, onSearchClick: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to MovieApp!", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onPopularClick, modifier = Modifier.fillMaxWidth()) {
                Text("View Popular Movies")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onSearchClick, modifier = Modifier.fillMaxWidth()) {
                Text("Search Movies")
            }
        }
    }

    @Composable
    fun PopularMovies(viewModel: MovieViewModel) {
        val movies = viewModel.movies.observeAsState(emptyList()).value

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(movies) { movie ->
                MovieItem(movie)
            }
        }
    }    @Composable
    fun MovieItem(movie: Movie) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),

        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = movie.title, style = MaterialTheme.typography.titleLarge)
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3
                )
            }
        }
    }
}