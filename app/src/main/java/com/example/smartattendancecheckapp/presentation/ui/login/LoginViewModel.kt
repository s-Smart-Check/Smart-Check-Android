package com.example.smartattendancecheckapp.presentation.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartattendancecheckapp.domain.model.request.LoginData
import com.example.smartattendancecheckapp.domain.usecase.LoadTestListUseCase
import com.example.smartattendancecheckapp.domain.usecase.RequestLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class LoginState(val state: Int) {
    SUCCESS(0),
    FAIL(1)
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loadTestListUseCase: LoadTestListUseCase,
    private val requestLoginUseCase: RequestLoginUseCase
): ViewModel() {

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState

    fun requestTest() {
        viewModelScope.launch {
            loadTestListUseCase()
                .onSuccess {
                    _loginState.value = LoginState.SUCCESS
                }
                .onFailure {
                    _loginState.value = LoginState.FAIL
                }
        }
    }

    fun requestLogin(loginData: LoginData) {
        viewModelScope.launch {
            requestLoginUseCase(loginData)
                .onSuccess {
                    _loginState.value = LoginState.SUCCESS
                }
                .onFailure {
                    _loginState.value = LoginState.FAIL
                }
        }
    }
}