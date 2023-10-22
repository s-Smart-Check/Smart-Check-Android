package com.example.smartattendancecheckapp.domain.repo

import com.example.smartattendancecheckapp.domain.model.request.AttendanceCalendar
import com.example.smartattendancecheckapp.domain.model.response.AttendanceCalendarRes

interface CalendarCheckRepository {
    suspend fun loadCalendarCheck(attendanceCalendar: AttendanceCalendar): Result<AttendanceCalendarRes>
}