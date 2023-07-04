package com.example.smartattendancecheckapp.ui.attendcheck

import android.Manifest
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.smartattendancecheckapp.R
import com.example.smartattendancecheckapp.databinding.FragmentAttendCheckBinding
import com.example.smartattendancecheckapp.model.TestList
import com.example.smartattendancecheckapp.model.request.StudentAttendanceData
import com.example.smartattendancecheckapp.model.response.StudentAttendanceRes
import com.example.smartattendancecheckapp.network.RetrofitClient
import com.example.smartattendancecheckapp.ui.enrollface.EnrollFaceFragment
import com.example.smartattendancecheckapp.ui.main2.MainActivity2
import com.example.smartattendancecheckapp.ui.Login.usrNum
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class AttendCheckFragment : Fragment() {

    private lateinit var binding : FragmentAttendCheckBinding
    private lateinit var navController: NavController

    // 카메라를 실행한 후 찍은 사진을 저장
    var pictureUri: Uri? = null
    private val getTakePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) {

    }

    // 요청하고자 하는 권한들
    private val permission = arrayOf(Manifest.permission.CAMERA)

    // 권한을 허용하도록 요청
    private val requestMultiplePermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
        results.forEach {
            if(!it.value) {
                Toast.makeText(context, "권한 허용 필요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAttendCheckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        navController = findNavController()
        requestMultiplePermission.launch(permission)

        binding.attendCheckBtnAddFace.setOnClickListener {
            navController.navigate(R.id.action_attent_check_to_enroll)
        }

        binding.refreshLayout.setOnRefreshListener {
            // 실제 통신
//            RetrofitClient.retrofitService.requestAttendanceInfo(StudentAttendanceData(usrNum)).enqueue(object : retrofit2.Callback<StudentAttendanceRes> {
//                override fun onResponse(call: Call<StudentAttendanceRes>, response: Response<StudentAttendanceRes>) {
//
//                    if(response.isSuccessful) {
//                        when(response.code()) {
//                            200 -> {
//                                // 통신 성공
//                                when(response.body()!!.attendance) {
//                                    true -> {
//                                        Toast.makeText(activity, "출석 완료!", Toast.LENGTH_SHORT).show()
//
//                                        if(response.body()!!.state == 1) {
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
//                                        Toast.makeText(activity, "출석 실패", Toast.LENGTH_SHORT).show()
//
//                                        binding.attendCheckImage.setImageResource(R.drawable.ic_baseline_cancel_24)
//                                        binding.testText.text = ""
//
//                                        binding.attendCheckClassName.text=""
//                                        binding.attendCheckProfessor.text=""
//
//                                        binding.refreshLayout.isRefreshing = false
//                                    }
//                                }
//                            }
//                            400 ->{
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

            // 테스트 통신
            RetrofitClient.retrofitService.getTestList().enqueue(object : retrofit2.Callback<TestList> {
                override fun onResponse(call: Call<TestList>, response: Response<TestList>) {
//                      통신 성공
                    Toast.makeText(activity, "출석 완료!", Toast.LENGTH_SHORT).show()

                    binding.attendCheckImage.setImageResource(R.drawable.ic_baseline_check_circle_24)
                    binding.testText.text = "출석 완료"

                    binding.attendCheckClassName.text="창의적 공학 설계"
                    binding.attendCheckProfessor.text="김시현" + " 교수님"

                    binding.attendCheckCardviewWarning.isVisible = true

                    binding.refreshLayout.isRefreshing = false

                }

                override fun onFailure(call: Call<TestList>, t: Throwable) {
//                      통신 실패
                    Toast.makeText(activity, "출석 실패", Toast.LENGTH_SHORT).show()
                }

            })
        }

    }

}