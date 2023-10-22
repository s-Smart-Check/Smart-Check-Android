package com.example.smartattendancecheckapp.presentation.ui.calendarcheck

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.smartattendancecheckapp.databinding.FragmentCalendarCheckBinding
import com.example.smartattendancecheckapp.domain.model.request.AttendanceCalendar
import com.example.smartattendancecheckapp.presentation.ui.enrollface.EnrollFaceViewModel
import com.example.smartattendancecheckapp.presentation.ui.login.usrNum

class CalendarCheckFragment : Fragment() {

    private lateinit var binding: FragmentCalendarCheckBinding
    private val viewModel = ViewModelProvider(this)[CalendarCheckViewModel::class.java]

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarCheckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.apply {

            calendar.setOnDateChangeListener { _, year, month, day ->
                var dateString = ""
                var myMonth = "${month + 1}"
                var myDay = "$day"

                if (month < 10)
                    myMonth = "0$myMonth"
                if (day < 10)
                    myDay = "0$day"

                dateString = "$year-$myMonth-$myDay"

                tvCalendarDate.text = "${year}년 ${month + 1}월 ${day}일"

                viewModel.requestCalendarCheck(AttendanceCalendar(dateString, usrNum))
                viewModel.attendanceCalendarRes.observe(viewLifecycleOwner) {
                    when(it.className) {
                        null -> {
                            tvCalendarClassName.isVisible = false
                            tvCalendarProfessorName.isVisible = false
                            tvCalendarAttendance.isVisible = false
                        }
                        else -> {
                            tvCalendarClassName.isVisible = true
                            tvCalendarProfessorName.isVisible = true
                            tvCalendarAttendance.isVisible = true

                            tvCalendarClassName.text = "수업 명: ${it.className}"
                            tvCalendarProfessorName.text = "교수 명: ${it.professor}"

                            when(it.attendance) {
                                true -> tvCalendarAttendance.text = "출석 결과: 출석"
                                false -> tvCalendarAttendance.text = "출석 결과: 결석"
                            }
                        }
                    }
                }
            }
        }
    }
}