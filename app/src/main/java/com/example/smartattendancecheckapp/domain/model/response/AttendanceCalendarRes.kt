package com.example.smartattendancecheckapp.domain.model.response

data class AttendanceCalendarRes(
    val className: String,
    val professor: String,
    val attendance: Boolean
)
