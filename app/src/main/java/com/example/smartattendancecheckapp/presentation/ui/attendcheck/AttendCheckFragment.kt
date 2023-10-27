package com.example.smartattendancecheckapp.presentation.ui.attendcheck

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.smartattendancecheckapp.R
import com.example.smartattendancecheckapp.databinding.FragmentAttendCheckBinding
import com.example.smartattendancecheckapp.domain.model.request.StudentAttendanceData
import com.example.smartattendancecheckapp.presentation.ui.login.usrNum
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AttendCheckFragment : Fragment() {

    private lateinit var binding : FragmentAttendCheckBinding
    private val permission = arrayOf(Manifest.permission.CAMERA)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAttendCheckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this)[AttendCheckViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        requestMultiplePermission.launch(permission)


//        if(viewModel.attendState) {
//            if (viewModel.attendWay == 1) {
//                binding.attendCheckCardviewWarning.isVisible = true
//            }
//            binding.attendCheckImage.setImageResource(R.drawable.ic_baseline_check_circle_24)
//            binding.testText.text = "출석 완료"
//
//            binding.attendCheckClassName.text="창의적 공학 설계"
//            binding.attendCheckProfessor.text="김시현" + " 교수님"
//        } else {
//            binding.attendCheckImage.setImageResource(R.drawable.ic_baseline_cancel_24)
//            binding.testText.text = ""
//            binding.attendCheckClassName.text=""
//            binding.attendCheckProfessor.text=""
//        }

        binding.btnCheckAttend.setOnClickListener {
//             테스트 통신
            viewModel.requestTest()

//            실제 통신
//            viewModel.requestAttendCheck(StudentAttendanceData(usrNum))
        }

        binding.refreshLayout.setOnRefreshListener {
            // 실제 통신
//            NetWorkModule.retrofitService.requestAttendanceInfo(StudentAttendanceData(usrNum)).enqueue(object : retrofit2.Callback<StudentAttendanceRes> {
//                override fun onResponse(call: Call<StudentAttendanceRes>, response: Response<StudentAttendanceRes>) {
//
//                    if(response.isSuccessful) {
//                        when(response.code()) {
//                            200 -> {
//                                // 통신 성공
//                                when(response.body()!!.attendance) {
//                                    true -> {
//                                        viewModel.attendState = true
//                                        Toast.makeText(activity, "출석 완료!", Toast.LENGTH_SHORT).show()
//
//                                        if(response.body()!!.state == 1) {
//                                            viewModel.attendWay = 1
//                                            Toast.makeText(activity, "얼굴 재등록 필요!!", Toast.LENGTH_SHORT).show()
//                                            binding.attendCheckCardviewWarning.isVisible = true
//                                        }
//
//                                        binding.attendCheckImage.setImageResource(R.drawable.ic_baseline_check_circle_24)
//                                        binding.testText.text = "출석 완료"
//
//                                        binding.attendCheckClassName.text="${response.body()!!.className}"
//                                        binding.attendCheckProfessor.text="${response.body()!!.professor}" + " 교수님"
//
//                                        binding.refreshLayout.isRefreshing = false
//                                    }
//                                    false -> {
//                                        viewModel.attendState = false
//                                        Toast.makeText(activity, "출석 실패", Toast.LENGTH_SHORT).show()
//
//                                        binding.attendCheckImage.setImageResource(R.drawable.ic_baseline_cancel_24)
//                                        binding.testText.text = ""
//
//                                        binding.attendCheckClassName.text=""
//                                        binding.attendCheckProfessor.text=""
//
//                                        binding.attendCheckCardviewWarning.isVisible = false
//                                        binding.refreshLayout.isRefreshing = false
//                                    }
//                                }
//                            }
//                            400 -> {
//                                Toast.makeText(activity, "출석 실패", Toast.LENGTH_SHORT).show()
//                                binding.refreshLayout.isRefreshing = false
//                            }
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<StudentAttendanceRes>, t: Throwable) {
//                    // 통신 실패
//                    Toast.makeText(activity, "출석 실패", Toast.LENGTH_SHORT).show()
//                }
//
//            })

//             테스트 통신
            viewModel.requestTest()

//            실제 통신
//            viewModel.requestAttendCheck(StudentAttendanceData(usrNum))

            viewModel.studentAttendanceRes.observe(viewLifecycleOwner) { attendanceInfo ->
                when(attendanceInfo.attendance) {
                    true -> {
                        binding.attendCheckImage.setImageResource(R.drawable.ic_baseline_check_circle_24)
//                        binding.btnCheckAttend.setBackgroundColor(android.graphics.Color.parseColor("#8900ff"))
                        binding.btnCheckAttend.setBackgroundResource(R.drawable.color_attended)

                        binding.attendCheckClassName.text= attendanceInfo.className
                        binding.attendCheckProfessor.text= attendanceInfo.professor

                        binding.refreshLayout.isRefreshing = false
                    }
                    false -> {
                        Toast.makeText(activity, "출석 실패", Toast.LENGTH_SHORT).show()

                        binding.attendCheckImage.setImageResource(R.drawable.ic_baseline_cancel_24)

                        binding.attendCheckClassName.text=""
                        binding.attendCheckProfessor.text=""

                        binding.attendCheckCardviewWarning.isVisible = false
                        binding.refreshLayout.isRefreshing = false
                    }
                }

                when(attendanceInfo.state) {
                    0 -> {
                        binding.attendCheckCardviewWarning.isVisible = false
                    }
                    1 -> {
                        Toast.makeText(activity, "얼굴 재등록 필요!!", Toast.LENGTH_SHORT).show()
                        binding.attendCheckCardviewWarning.isVisible = true
                    }
                }
            }
        }

    }

    private val requestMultiplePermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
        results.forEach {
            if(!it.value) {
                Toast.makeText(context, "권한 허용 필요", Toast.LENGTH_SHORT).show()
            }
        }
    }

}