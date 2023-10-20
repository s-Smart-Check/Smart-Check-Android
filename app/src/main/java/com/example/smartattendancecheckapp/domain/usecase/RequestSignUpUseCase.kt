package com.example.smartattendancecheckapp.domain.usecase

import com.example.smartattendancecheckapp.domain.model.request.SignUpData
import com.example.smartattendancecheckapp.domain.model.response.SignUpRes
import com.example.smartattendancecheckapp.domain.repo.SignUpRepository

class RequestSignUpUseCase (private val repo: SignUpRepository) {
    suspend operator fun invoke(signUpData: SignUpData): Result<SignUpRes> {
        return repo.requestSignUp(signUpData)
    }
}