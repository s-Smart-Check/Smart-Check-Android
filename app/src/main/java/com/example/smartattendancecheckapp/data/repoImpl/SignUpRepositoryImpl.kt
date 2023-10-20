package com.example.smartattendancecheckapp.data.repoImpl

import com.example.smartattendancecheckapp.data.api.NetworkApi
import com.example.smartattendancecheckapp.domain.model.HttpError
import com.example.smartattendancecheckapp.domain.model.request.SignUpData
import com.example.smartattendancecheckapp.domain.model.response.SignUpRes
import com.example.smartattendancecheckapp.domain.repo.SignUpRepository
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val api: NetworkApi
): SignUpRepository {
    override suspend fun requestSignUp(signUpData: SignUpData): Result<SignUpRes> {
        return try {
            val response = api.requestSignUp(signUpData)
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