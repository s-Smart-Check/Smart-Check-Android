package com.example.smartattendancecheckapp.domain.model.response

data class StudentAttendanceRes(
    var className: String,
    var professor: String,
    var attendance: Boolean,
    var state: Int
)
