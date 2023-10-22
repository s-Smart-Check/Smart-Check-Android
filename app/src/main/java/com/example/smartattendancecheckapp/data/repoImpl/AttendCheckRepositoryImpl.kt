package com.example.smartattendancecheckapp.data.repoImpl

import com.example.smartattendancecheckapp.data.api.NetworkApi
import com.example.smartattendancecheckapp.domain.model.HttpError
import com.example.smartattendancecheckapp.domain.model.request.StudentAttendanceData
import com.example.smartattendancecheckapp.domain.model.response.StudentAttendanceRes
import com.example.smartattendancecheckapp.domain.repo.AttendCheckRepository
import javax.inject.Inject

class AttendCheckRepositoryImpl @Inject constructor(
    private val api: NetworkApi
): AttendCheckRepository {
    override suspend fun loadAttendCheck(usrNum: StudentAttendanceData): Result<StudentAttendanceRes> {
        return try {
            val response = api.requestAttendanceInfo(usrNum)
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