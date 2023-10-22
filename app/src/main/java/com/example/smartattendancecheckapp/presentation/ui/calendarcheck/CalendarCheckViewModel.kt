package com.example.smartattendancecheckapp.presentation.ui.calendarcheck

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartattendancecheckapp.domain.model.request.AttendanceCalendar
import com.example.smartattendancecheckapp.domain.model.response.AttendanceCalendarRes
import com.example.smartattendancecheckapp.domain.usecase.LoadCalendarCheckUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarCheckViewModel @Inject constructor(
    private val loadCalendarCheckUseCase: LoadCalendarCheckUseCase
): ViewModel(){

    private val _attendanceCalendarRes = MutableLiveData<AttendanceCalendarRes>()
    val attendanceCalendarRes: LiveData<AttendanceCalendarRes> = _attendanceCalendarRes

    fun requestCalendarCheck(attendanceCalendar: AttendanceCalendar) {
        viewModelScope.launch {
            loadCalendarCheckUseCase(attendanceCalendar)
                .onSuccess {
                    _attendanceCalendarRes.value = it
                }
                .onFailure {

                }
        }
    }
}