package com.example.smartattendancecheckapp.domain.repo

import com.example.smartattendancecheckapp.domain.model.request.StudentAttendanceData
import com.example.smartattendancecheckapp.domain.model.response.StudentAttendanceRes

interface AttendCheckRepository {
    suspend fun loadAttendCheck(usrNum: StudentAttendanceData): Result<StudentAttendanceRes>
}