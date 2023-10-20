package com.example.smartattendancecheckapp.domain.repo

import com.example.smartattendancecheckapp.domain.model.request.SignUpData
import com.example.smartattendancecheckapp.domain.model.response.SignUpRes

interface SignUpRepository {
    suspend fun requestSignUp(signUpData: SignUpData): Result<SignUpRes>
}