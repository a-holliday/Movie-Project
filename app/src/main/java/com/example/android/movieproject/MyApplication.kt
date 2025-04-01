package com.example.android.movieproject;

import android.app.Application;
import com.example.android.movieproject.di.AppContainer

public class MyApplication: Application() {
    companion object {
        lateinit var appContainer: AppContainer
    }

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer()
    }

}
