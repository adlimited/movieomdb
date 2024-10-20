package com.weijie.moview.viewmodel

import androidx.lifecycle.ViewModel
import com.weijie.moview.utility.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    fun saveLoginState(isLoggedIn: Boolean) {
        sessionManager.saveLoginState(isLoggedIn)
    }

    fun isLoggedIn(): Boolean {
        return sessionManager.isLoggedIn()
    }
}