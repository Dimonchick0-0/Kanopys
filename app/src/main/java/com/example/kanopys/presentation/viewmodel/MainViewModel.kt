package com.example.kanopys.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kanopys.data.repository.RepositoryImpl
import com.example.kanopys.domain.entity.User
import com.example.kanopys.presentation.state.StateAuthentication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repositoryImpl: RepositoryImpl
) : ViewModel() {

    private val _state = MutableLiveData<StateAuthentication>()
    val state: LiveData<StateAuthentication>
        get() = _state

    suspend fun authUser(email: String, password: String): User {
        val validateEmail = validateEmail(email)
        val validatePassword = validatePassword(password)
        return withContext(Dispatchers.IO) {
            getUser(validateEmail, validatePassword)
        }
    }

    private suspend fun getUser(email: String, password: String): User {
        return withContext(Dispatchers.IO) {
            val user = repositoryImpl.authenticationUser(email, password)
            return@withContext user
        }
    }

    fun showError(email: String, password: String): Boolean {
        var result = true
        if (email.isEmpty()) {
            result = false
            _state.value = StateAuthentication.Error
        }
        if (password.isEmpty()) {
            result = false
            _state.value = StateAuthentication.Error
        }
        return result
    }

    private fun validateEmail(email: String): String {
        return email.trim().filter { !it.isWhitespace() }
    }

    private fun validatePassword(password: String): String {
        return password.trim().filter { !it.isWhitespace() }
    }

    companion object {
        private const val TAG = "TESTAUTH"
    }
}