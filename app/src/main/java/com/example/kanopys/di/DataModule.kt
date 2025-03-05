package com.example.kanopys.di

import android.app.Application
import com.example.kanopys.data.database.AppDatabase
import com.example.kanopys.data.database.ProfileUserDao
import com.example.kanopys.data.localdatasoure.LocalDataSource
import com.example.kanopys.data.localdatasoure.LocalDataSourceImpl
import com.example.kanopys.data.repository.RepositoryImpl
import com.example.kanopys.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @Binds
    fun bindLocalDataSource(impl: LocalDataSourceImpl): LocalDataSource

    @ApplicationScope
    @Binds
    fun bindUserRepository(impl: RepositoryImpl): UserRepository

    companion object {
        @Provides
        fun provideProfileUserDao(application: Application): ProfileUserDao {
            return AppDatabase.getInstance(application).userDao()
        }
    }
}