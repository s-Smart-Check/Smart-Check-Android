package com.example.smartattendancecheckapp.data.repoImpl

import com.example.smartattendancecheckapp.data.api.NetworkApi
import com.example.smartattendancecheckapp.domain.model.HttpError
import com.example.smartattendancecheckapp.domain.model.request.LoginData
import com.example.smartattendancecheckapp.domain.model.response.LoginRes
import com.example.smartattendancecheckapp.domain.repo.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val api: NetworkApi
): LoginRepository{

    override suspend fun requestLogin(loginData: LoginData): Result<LoginRes> {
        return try {
            val response = api.requestLogin(loginData)
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