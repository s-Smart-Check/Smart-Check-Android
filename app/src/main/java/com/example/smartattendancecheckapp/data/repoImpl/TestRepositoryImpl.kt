package com.example.smartattendancecheckapp.data.repoImpl

import com.example.smartattendancecheckapp.data.api.NetworkApi
import com.example.smartattendancecheckapp.domain.model.HttpError
import com.example.smartattendancecheckapp.domain.model.TestList
import com.example.smartattendancecheckapp.domain.repo.TestRepository
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(
    private val api: NetworkApi
): TestRepository {
    override suspend fun getTestList(): Result<TestList> {
        return try {
            val response = api.getTestList()
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