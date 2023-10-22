package com.example.smartattendancecheckapp.domain.usecase

import com.example.smartattendancecheckapp.domain.model.request.AttendanceCalendar
import com.example.smartattendancecheckapp.domain.model.response.AttendanceCalendarRes
import com.example.smartattendancecheckapp.domain.repo.CalendarCheckRepository

class LoadCalendarCheckUseCase (private val repo: CalendarCheckRepository) {
    suspend operator fun invoke(attendanceCalendar: AttendanceCalendar): Result<AttendanceCalendarRes> {
        return repo.loadCalendarCheck(attendanceCalendar)
    }
}