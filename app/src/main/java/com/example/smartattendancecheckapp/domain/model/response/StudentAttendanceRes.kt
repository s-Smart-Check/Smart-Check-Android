package com.example.smartattendancecheckapp.domain.model.response

data class StudentAttendanceRes(
    var className: String,
    var professor: String,
    var attendance: Boolean,
    var state: Int              // 출석 방식(0 == 얼굴 인식, 1 == 웹을 통한 임의 출석)
)
