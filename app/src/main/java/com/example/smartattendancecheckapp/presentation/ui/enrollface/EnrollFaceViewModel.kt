package com.example.smartattendancecheckapp.presentation.ui.enrollface

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartattendancecheckapp.domain.usecase.RequestEnrollFaceUseCase
import com.example.smartattendancecheckapp.presentation.ui.signup.EnrollFaceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class EnrollFaceViewModel @Inject constructor(
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