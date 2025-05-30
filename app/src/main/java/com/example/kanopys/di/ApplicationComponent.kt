package com.example.kanopys.di

import android.app.Application
import com.example.kanopys.presentation.activity.MainActivity
import com.example.kanopys.presentation.fragment.FragmentAuthorization
import com.example.kanopys.presentation.fragment.FragmentRegister
import com.example.kanopys.presentation.fragment.MovieFragment
import com.example.kanopys.presentation.fragment.ProfileFragment
import com.example.kanopys.presentation.fragment.ScreenMoviesFragment
import com.example.kanopys.presentation.fragment.SearchMovieFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [ViewModelModule::class, DataModule::class])
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(fragmentRegister: FragmentRegister)
    fun inject(fragmentRegister: FragmentAuthorization)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: ScreenMoviesFragment)
    fun inject(fragment: SearchMovieFragment)
    fun inject(fragment: MovieFragment)

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}