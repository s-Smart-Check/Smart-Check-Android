package com.example.smartattendancecheckapp.domain.usecase

import com.example.smartattendancecheckapp.domain.repo.EnrollFaceRepository
import okhttp3.MultipartBody

class RequestEnrollFaceUseCase (private val repo: EnrollFaceRepository){
    suspend operator fun invoke(studentNum: String, imageFile: List<MultipartBody.Part>): Result<String> {
        return repo.requestEnrollFace(studentNum, imageFile)
    }
}