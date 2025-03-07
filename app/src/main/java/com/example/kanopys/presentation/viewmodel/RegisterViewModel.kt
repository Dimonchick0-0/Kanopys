package com.example.kanopys.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanopys.data.repository.RepositoryImpl
import com.example.kanopys.domain.entity.User
import com.example.kanopys.presentation.state.StateAuthentication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val repositoryImpl: RepositoryImpl
) : ViewModel() {

    private var _userRegister = MutableLiveData<User>()

    private var _state = MutableLiveData<StateAuthentication>()
    val state: LiveData<StateAuthentication>
        get() = _state

    fun registerUser(name: String, email: String, password: String) {
        val nameUser = validateNameUser(name)
        val passwordUser = validatePasswordUser(password)
        val emailUser = validateEmailUser(email)
        viewModelScope.launch {
            val newUser = User(name = nameUser, password = passwordUser, email = emailUser)
            _userRegister.value = newUser
            repositoryImpl.registerAProfile(newUser)
        }
    }

    suspend fun checkForEmail(emailUser: String): Boolean {
        return withContext(Dispatchers.Default) {
            repositoryImpl.checkForEmail(emailUser)
        }
    }

    fun showError(
        name: String,
        password: String,
        repeatPassword: String,
        email: String
    ): Boolean {
        var result = true
        if (name.isEmpty()) {
            result = false
            _state.value = StateAuthentication.Error
        }
        if (password.isEmpty()) {
            result = false
            _state.value = StateAuthentication.Error
        }
        if (repeatPassword.isEmpty()) {
            result = false
            _state.value = StateAuthentication.Error
        }
        if (email.isEmpty()) {
            result = false
            _state.value = StateAuthentication.Error
        }
        return result
    }

    private fun validateNameUser(name: String?): String {
        return name?.trim()?.filter { !it.isWhitespace() } ?: ""
    }

    private fun validatePasswordUser(password: String?): String {
        return password?.trim()?.filter { !it.isWhitespace() } ?: ""
    }

    private fun validateEmailUser(email: String?): String {
        return email?.trim()?.filter { !it.isWhitespace() } ?: ""
    }

    companion object {
        private const val TAG = "TESTREGISTER"
    }
}