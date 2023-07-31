package com.example.smartattendancecheckapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.smartattendancecheckapp.databinding.FragmentCalendarCheckBinding
import com.example.smartattendancecheckapp.model.TestList
import com.example.smartattendancecheckapp.model.response.AttendanceCalendarRes
import com.example.smartattendancecheckapp.network.RetrofitClient
import com.example.smartattendancecheckapp.network.RetrofitClient.retrofitService
import com.example.smartattendancecheckapp.ui.main2.MainActivity2
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
                var dateString = "$year$month$day"

                Log.d("캘린더 뷰 태그", dateString)

                retrofitService.getDateAttendance(dateString).enqueue(object : retrofit2.Callback<AttendanceCalendarRes> {
                    // 정상적으로 응답이 온 경우
                    override fun onResponse(call: Call<AttendanceCalendarRes>, response: Response<AttendanceCalendarRes>) {

                    }
                    // 통신에 실패한 경우
                    override fun onFailure(call: Call<AttendanceCalendarRes>, t: Throwable) {

                    }
                })
            }
        }
    }
}