package com.example.smartattendancecheckapp.model.response

data class StudentAttendanceRes(
    var className: String,
    var professor: String,
    var attendance: Boolean
)
