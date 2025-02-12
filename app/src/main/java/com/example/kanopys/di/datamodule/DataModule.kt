package com.example.kanopys.di.datamodule

import android.app.Application
import com.example.kanopys.data.database.AppDatabase
import com.example.kanopys.data.database.ProfileUserDao
import com.example.kanopys.data.localdatastore.LocalDataSource
import com.example.kanopys.data.localdatastore.LocalDataSourceImpl
import com.example.kanopys.data.repository.RepositoryImpl
import com.example.kanopys.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindLocalDataSource(impl: LocalDataSourceImpl): LocalDataSource

    companion object {
        @Provides
        fun provideUserDao(application: Application): ProfileUserDao {
            return AppDatabase.getInstance(application).userDao()
        }
        @Provides
        fun provideRepository(): UserRepository {
            return RepositoryImpl.getInstance()
        }
    }
}