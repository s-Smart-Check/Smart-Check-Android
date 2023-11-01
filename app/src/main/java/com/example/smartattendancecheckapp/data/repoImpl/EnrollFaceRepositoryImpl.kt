package com.example.smartattendancecheckapp.data.repoImpl

import com.example.smartattendancecheckapp.data.api.NetworkApi
import com.example.smartattendancecheckapp.domain.model.HttpError
import com.example.smartattendancecheckapp.domain.repo.EnrollFaceRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class EnrollFaceRepositoryImpl @Inject constructor(
    private val api: NetworkApi
): EnrollFaceRepository {
    override suspend fun requestEnrollFace(
        studentNum: String,
        imageFile: List<MultipartBody.Part>,
    ): Result<String> {
        return try {
//            val response = api.sendImage(studentNum, imageFile)
//            when {
//                response.isSuccessful -> Result.success(response.body()!!)
//                else -> {
//                    Result.failure(HttpError(response.code(), response.errorBody()?.string() ?: ""))
//                }
//            }
            val response = api.sendImage(studentNum, imageFile)
            when {
                response.isSuccessful -> Result.success(response.body()!!)
                else -> {
                    Result.failure(HttpError(response.code(), response.errorBody()?.string() ?: ""))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}