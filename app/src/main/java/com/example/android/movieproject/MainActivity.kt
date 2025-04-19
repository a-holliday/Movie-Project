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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
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
    fun MovieAppNavHost(navController: NavHostController, viewModel: MovieViewModel) {
        NavHost(navController = navController, startDestination = "landing") {
            composable("landing") {
                MovieLandingScreen(
                    onPopularClick = {
                        navController.navigate("popular")
                        viewModel.onPopularClicked()
                    },
                    onSearchClick = {
                        navController.navigate("searchLanding")
                    }
                )
            }
            composable("popular") {
                PopularMovies(viewModel)
            }
            composable("searchLanding"){
                SearchScreen(viewModel)
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
    }

    @Composable
    fun MovieItem(movie: Movie) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),

        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = movie.title.toString(), style = MaterialTheme.typography.titleLarge)
                Text(
                    text = movie.overview.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3
                )
            }
        }
    }


    @Composable
    fun SearchScreen(viewModel: MovieViewModel) {
        var query by remember { mutableStateOf("") }
        val movies = viewModel.movies.observeAsState(emptyList()).value


        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Search movies, shows...") },
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onSearch = {
                        viewModel.searchMovies(query) //
                        defaultKeyboardAction(ImeAction.Search)
                    }
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(movies) { movie ->
                    MovieItem(movie)
                }
            }
        }
    }


}



