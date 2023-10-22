package com.example.smartattendancecheckapp.data.repoImpl

import com.example.smartattendancecheckapp.data.api.NetworkApi
import com.example.smartattendancecheckapp.domain.model.HttpError
import com.example.smartattendancecheckapp.domain.model.request.AttendanceCalendar
import com.example.smartattendancecheckapp.domain.model.response.AttendanceCalendarRes
import com.example.smartattendancecheckapp.domain.repo.CalendarCheckRepository
import javax.inject.Inject

class CalendarCheckRepositoryImpl @Inject constructor(
    private val api: NetworkApi
): CalendarCheckRepository {
    override suspend fun loadCalendarCheck(attendanceCalendar: AttendanceCalendar): Result<AttendanceCalendarRes> {
        return try {
            val response = api.getDateAttendance(attendanceCalendar)
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