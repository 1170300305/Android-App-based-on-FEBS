package com.hit.bilthas.myapp1

import android.app.Application
import android.util.Log

class MyAppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("hello", "this is application class")
    }

    override fun onTerminate() {
        Log.d("hello", "application terminated")
        super.onTerminate()
    }
}