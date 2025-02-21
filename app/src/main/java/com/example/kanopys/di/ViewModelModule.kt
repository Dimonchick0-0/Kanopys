package com.example.kanopys.di

import androidx.lifecycle.ViewModel
import com.example.kanopys.presentation.viewmodel.MainViewModel
import com.example.kanopys.presentation.viewmodel.RegisterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    @Binds
    fun bindRegisterViewModel(viewModel: RegisterViewModel): ViewModel
}