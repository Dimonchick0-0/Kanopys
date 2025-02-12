package com.example.kanopys.di

import com.example.kanopys.di.datamodule.DataModule
import dagger.Component

@Component(modules = [DataModule::class])
interface ApplicationComponent {

}