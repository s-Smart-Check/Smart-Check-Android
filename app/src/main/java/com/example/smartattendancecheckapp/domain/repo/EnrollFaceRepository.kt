package com.example.smartattendancecheckapp.domain.repo

import okhttp3.MultipartBody

interface EnrollFaceRepository {
    suspend fun requestEnrollFace(studentNum: String, imageFile: List<MultipartBody.Part>): Result<String>
}