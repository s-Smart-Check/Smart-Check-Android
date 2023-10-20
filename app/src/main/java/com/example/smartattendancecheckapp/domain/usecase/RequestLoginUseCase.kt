package com.example.smartattendancecheckapp.domain.usecase

import com.example.smartattendancecheckapp.domain.model.request.LoginData
import com.example.smartattendancecheckapp.domain.model.response.LoginRes
import com.example.smartattendancecheckapp.domain.repo.LoginRepository

class RequestLoginUseCase(private val repo: LoginRepository) {
    suspend operator fun invoke(loginData: LoginData): Result<LoginRes> {
        return repo.requestLogin(loginData)
    }
}