package com.example.kanopys.presentation

import android.app.Application
import com.example.kanopys.di.DaggerApplicationComponent

class KanopysApplication: Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}