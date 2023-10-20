package com.example.smartattendancecheckapp.domain.repo

import com.example.smartattendancecheckapp.domain.model.TestList

interface TestRepository {
    suspend fun getTestList(): Result<TestList>
}