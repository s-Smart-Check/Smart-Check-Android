package com.example.smartattendancecheckapp.presentation.ui.calendarcheck

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.smartattendancecheckapp.databinding.FragmentCalendarCheckBinding
import com.example.smartattendancecheckapp.domain.model.request.AttendanceCalendar
import com.example.smartattendancecheckapp.presentation.ui.login.usrNum
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalendarCheckFragment : Fragment() {

    private lateinit var binding: FragmentCalendarCheckBinding

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

        val viewModel = ViewModelProvider(this)[CalendarCheckViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.apply {

            calendar.addDecorator(TodayDecorator())
            calendar.setOnDateChangedListener { widget, date, selected ->
                var dateString = ""
                var myMonth = "${date.month}"
                var myDay = "${date.day}"

                if (date.month < 10)
                    myMonth = "0$myMonth"
                if (date.day < 10)
                    myDay = "0${date.day}"

                dateString = "${date.year}-$myMonth-$myDay"

                tvCalendarDate.text = "${date.year}년 ${date.month}월 ${date.day}일"

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
//            calendar.selectedDates { _, year, month, day ->
//                var dateString = ""
//                var myMonth = "${month + 1}"
//                var myDay = "$day"
//
//                if (month < 10)
//                    myMonth = "0$myMonth"
//                if (day < 10)
//                    myDay = "0$day"
//
//                dateString = "$year-$myMonth-$myDay"
//
//                tvCalendarDate.text = "${year}년 ${month + 1}월 ${day}일"
//
//                viewModel.requestCalendarCheck(AttendanceCalendar(dateString, usrNum))
//                viewModel.attendanceCalendarRes.observe(viewLifecycleOwner) {
//                    when(it.className) {
//                        null -> {
//                            tvCalendarClassName.isVisible = false
//                            tvCalendarProfessorName.isVisible = false
//                            tvCalendarAttendance.isVisible = false
//                        }
//                        else -> {
//                            tvCalendarClassName.isVisible = true
//                            tvCalendarProfessorName.isVisible = true
//                            tvCalendarAttendance.isVisible = true
//
//                            tvCalendarClassName.text = "수업 명: ${it.className}"
//                            tvCalendarProfessorName.text = "교수 명: ${it.professor}"
//
//                            when(it.attendance) {
//                                true -> tvCalendarAttendance.text = "출석 결과: 출석"
//                                false -> tvCalendarAttendance.text = "출석 결과: 결석"
//                            }
//                        }
//                    }
//                }
//            }
        }
    }

    inner class TodayDecorator: DayViewDecorator {
        private var date = CalendarDay.today()

        override fun shouldDecorate(day: CalendarDay?): Boolean {
            return day?.equals(date)!!
        }

        override fun decorate(view: DayViewFacade?) {
            view?.addSpan(StyleSpan(Typeface.BOLD))
            view?.addSpan(RelativeSizeSpan(1.5f))
            view?.addSpan(ForegroundColorSpan(Color.parseColor("#10345a")))
        }
    }
}