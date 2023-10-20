package com.example.smartattendancecheckapp.domain.usecase

import com.example.smartattendancecheckapp.domain.model.TestList
import com.example.smartattendancecheckapp.domain.repo.TestRepository

class LoadTestListUseCase(private val repo: TestRepository) {
    suspend operator fun invoke(): Result<TestList> {
        return repo.getTestList()
    }
}