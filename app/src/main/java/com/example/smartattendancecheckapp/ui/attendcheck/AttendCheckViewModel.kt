package com.example.smartattendancecheckapp.ui.attendcheck

import androidx.lifecycle.ViewModel

class AttendCheckViewModel: ViewModel() {

    var className: String? = null
    var professorName: String? = null
    var attendState: Boolean = false
    var attendWay: Int? = null
}