package com.example.smartattendancecheckapp.presentation.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartattendancecheckapp.domain.model.request.SignUpData
import com.example.smartattendancecheckapp.domain.usecase.LoadTestListUseCase
import com.example.smartattendancecheckapp.domain.usecase.RequestSignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SignUpState(val state: Int) {
    SUCCESS(0),
    FAIL(1)
}

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val loadTestListUseCase: LoadTestListUseCase,
    private val requestSignUpUseCase: RequestSignUpUseCase
): ViewModel() {
    private val _signUpState = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState> = _signUpState

    fun requestTest() {
        viewModelScope.launch {
            loadTestListUseCase()
                .onSuccess {
                    _signUpState.value = SignUpState.SUCCESS
                }
                .onFailure {
                    _signUpState.value = SignUpState.FAIL
                }
        }
    }

    fun requestSignUp(signUpData: SignUpData) {
        viewModelScope.launch {
            requestSignUpUseCase(signUpData)
                .onSuccess {
                    _signUpState.value = SignUpState.SUCCESS
                }
                .onFailure {
                    _signUpState.value = SignUpState.FAIL
                }
        }
    }
}