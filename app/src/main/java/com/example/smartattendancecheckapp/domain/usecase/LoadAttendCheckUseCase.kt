package com.example.smartattendancecheckapp.domain.usecase

import com.example.smartattendancecheckapp.domain.model.request.StudentAttendanceData
import com.example.smartattendancecheckapp.domain.model.response.StudentAttendanceRes
import com.example.smartattendancecheckapp.domain.repo.AttendCheckRepository

class LoadAttendCheckUseCase(private val repo: AttendCheckRepository){
    suspend operator fun invoke(usrNum: StudentAttendanceData): Result<StudentAttendanceRes> {
        return repo.loadAttendCheck(usrNum)
    }
}