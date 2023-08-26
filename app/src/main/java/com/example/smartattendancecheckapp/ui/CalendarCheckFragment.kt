package com.example.smartattendancecheckapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.smartattendancecheckapp.databinding.FragmentCalendarCheckBinding
import com.example.smartattendancecheckapp.model.request.AttendanceCalendar
import com.example.smartattendancecheckapp.model.response.AttendanceCalendarRes
import com.example.smartattendancecheckapp.network.RetrofitClient.retrofitService
import com.example.smartattendancecheckapp.ui.Login.usrNum
import retrofit2.Call
import retrofit2.Response

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

                retrofitService.getDateAttendance(AttendanceCalendar(dateString, usrNum)).enqueue(object : retrofit2.Callback<AttendanceCalendarRes> {
                    // 정상적으로 응답이 온 경우
                    override fun onResponse(call: Call<AttendanceCalendarRes>, response: Response<AttendanceCalendarRes>) {
                        if(response.isSuccessful) {
                            when(response.code()) {
                                200 -> {
                                    when (response.body()!!.className) {
                                        null -> {
                                            tvCalendarClassName.isVisible = false
                                            tvCalendarProfessorName.isVisible = false
                                            tvCalendarAttendance.isVisible = false
                                        }
                                        else -> {
                                            tvCalendarClassName.isVisible = true
                                            tvCalendarProfessorName.isVisible = true
                                            tvCalendarAttendance.isVisible = true

                                            tvCalendarClassName.text = "수업 명: ${response.body()!!.className}"
                                            tvCalendarProfessorName.text = "교수 명: ${response.body()!!.professor}"
                                            when (response.body()!!.attendance) {
                                                true -> tvCalendarAttendance.text = "출석 결과: 출석"
                                                false -> tvCalendarAttendance.text = "출석 결과: 결석"
                                            }
                                        }
                                    }
                                }
                                400 -> {
                                    Toast.makeText(activity, "오류 발생..", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                    }
                    // 통신에 실패한 경우
                    override fun onFailure(call: Call<AttendanceCalendarRes>, t: Throwable) {
                        Toast.makeText(activity, "다시 시도해주세요!", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
}