package com.example.smartattendancecheckapp.domain.repo

import com.example.smartattendancecheckapp.domain.model.request.LoginData
import com.example.smartattendancecheckapp.domain.model.response.LoginRes

interface LoginRepository {

    suspend fun requestLogin(loginData: LoginData): Result<LoginRes>
}