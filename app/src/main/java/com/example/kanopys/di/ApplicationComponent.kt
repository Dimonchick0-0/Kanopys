package com.example.kanopys.di

import android.app.Application
import com.example.kanopys.presentation.activity.MainActivity
import com.example.kanopys.presentation.fragment.FragmentAuthorization
import com.example.kanopys.presentation.fragment.FragmentRegister
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [ViewModelModule::class, DataModule::class])
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(fragmentRegister: FragmentRegister)
    fun inject(fragmentRegister: FragmentAuthorization)

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}