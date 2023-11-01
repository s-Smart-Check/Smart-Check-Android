package com.example.smartattendancecheckapp.presentation.ui.enrollface

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartattendancecheckapp.domain.usecase.RequestEnrollFaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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

    private val _uploadPhotoList = MutableLiveData<MutableList<Uri>>()
    val uploadPhotoList: LiveData<MutableList<Uri>> = _uploadPhotoList

    fun requestEnrollFace(
        studentNum: String,
        imageFiles: List<MultipartBody.Part>
    ) {
        runBlocking {
            requestEnrollFaceUseCase(studentNum, imageFiles)
                .onSuccess {
                    _enrollFaceState.value = EnrollFaceState.SUCCESS
                }
                .onFailure {
                    _enrollFaceState.value = EnrollFaceState.FAIL
                }
        }
    }

    fun addUploadPhoto(uri: Uri?) {
        if (_uploadPhotoList.value.isNullOrEmpty()) {
            _uploadPhotoList.value = mutableListOf(uri!!)
        }
        else {
            _uploadPhotoList.value!!.add(uri!!)
        }
    }

    fun removeUploadPhoto(position: Int) {
        val selectedPhotoList: MutableList<Uri> = _uploadPhotoList.value!!

        selectedPhotoList.removeAt(position)
        _uploadPhotoList.value = selectedPhotoList
    }
}