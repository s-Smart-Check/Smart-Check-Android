package com.example.smartattendancecheckapp.presentation.ui.attendcheck

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartattendancecheckapp.domain.model.request.StudentAttendanceData
import com.example.smartattendancecheckapp.domain.model.response.StudentAttendanceRes
import com.example.smartattendancecheckapp.domain.usecase.LoadAttendCheckUseCase
import com.example.smartattendancecheckapp.domain.usecase.LoadTestListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AttendCheckViewModel @Inject constructor(
    private val loadTestListUseCase: LoadTestListUseCase,
    private val loadAttendCheckUseCase: LoadAttendCheckUseCase
): ViewModel() {

    private val _studentAttendanceRes = MutableLiveData<StudentAttendanceRes>()
    val studentAttendanceRes: LiveData<StudentAttendanceRes> = _studentAttendanceRes

    fun requestTest() {
        viewModelScope.launch {
            loadTestListUseCase()
                .onSuccess {
                    _studentAttendanceRes.value =
                        StudentAttendanceRes(
                            className = "창의적 공학 설계",
                            professor = "김시현",
                            attendance = true,
                            state = 0
                        )
                }
                .onFailure {

                }
        }
    }


    fun requestAttendCheck(usrNum: StudentAttendanceData) {
        viewModelScope.launch {
            loadAttendCheckUseCase(usrNum)
                .onSuccess {
                    _studentAttendanceRes.value = it
                }
                .onFailure {

                }
        }
    }
}