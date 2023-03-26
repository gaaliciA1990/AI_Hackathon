package com.google.mlkit.vision.demo

import android.app.Application
import com.google.mlkit.vision.demo.catalog.Catalog


class MyApplication : Application() {
    val list: Catalog = Catalog()

    override fun onCreate() {
        super.onCreate()
    }

}