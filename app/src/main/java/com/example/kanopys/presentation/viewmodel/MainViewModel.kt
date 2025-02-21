package com.example.kanopys.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.kanopys.data.repository.RepositoryImpl
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repositoryImpl: RepositoryImpl
) : ViewModel() {}