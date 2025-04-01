package com.example.kanopys.di

import androidx.lifecycle.ViewModel
import com.example.kanopys.presentation.viewmodel.MainViewModel
import com.example.kanopys.presentation.viewmodel.MovieScreenViewModel
import com.example.kanopys.presentation.viewmodel.MovieViewModel
import com.example.kanopys.presentation.viewmodel.RegisterViewModel
import com.example.kanopys.presentation.viewmodel.SearchViewModel
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

    @IntoMap
    @ViewModelKey(MovieScreenViewModel::class)
    @Binds
    fun bindMovieScreenViewModel(viewModel: MovieScreenViewModel): ViewModel

    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    @Binds
    fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    @Binds
    fun bindMovieViewModel(viewModel: MovieViewModel): ViewModel
}