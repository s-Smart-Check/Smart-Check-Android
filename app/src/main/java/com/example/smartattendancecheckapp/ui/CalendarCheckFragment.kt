package com.example.smartattendancecheckapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartattendancecheckapp.databinding.FragmentCalendarCheckBinding

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
            calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->

                Log.d("캘린더 뷰 태그", "${year}년 ${month}월 ${dayOfMonth}일")
            }
        }
    }
}