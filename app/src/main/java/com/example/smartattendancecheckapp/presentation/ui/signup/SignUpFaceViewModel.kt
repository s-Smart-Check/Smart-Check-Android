package com.example.smartattendancecheckapp.presentation.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartattendancecheckapp.domain.usecase.RequestEnrollFaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

enum class EnrollFaceState(val state: Int) {
    SUCCESS(0),
    FAIL(1)
}

@HiltViewModel
class SignUpFaceViewModel @Inject constructor(
    private val requestEnrollFaceUseCase: RequestEnrollFaceUseCase
): ViewModel(){

    private val _enrollFaceState = MutableLiveData<EnrollFaceState>()
    val enrollFaceState: LiveData<EnrollFaceState> = _enrollFaceState

    fun requestEnrollFace(
        studentNum: String,
        imageFiles: List<MultipartBody.Part>
    ) {
        viewModelScope.launch {
            requestEnrollFaceUseCase(studentNum, imageFiles)
                .onSuccess {
                    _enrollFaceState.value = EnrollFaceState.SUCCESS
                }
                .onFailure {
                    _enrollFaceState.value = EnrollFaceState.FAIL
                }
        }
    }
}