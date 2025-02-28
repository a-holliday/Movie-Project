package com.example.android.movieproject;

import android.app.Application;
import com.example.android.movieproject.di.AppContainer

public class MyApplication: Application() {
    val appContainer = AppContainer()

}
