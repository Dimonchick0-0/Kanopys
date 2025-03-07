package com.example.kanopys.presentation.state

sealed interface StateAuthentication {
    data object Error: StateAuthentication
}