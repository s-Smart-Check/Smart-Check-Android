package com.example.smartattendancecheckapp.presentation.ui.signup

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.smartattendancecheckapp.R
import com.example.smartattendancecheckapp.databinding.ActivitySignupBinding
import com.example.smartattendancecheckapp.presentation.ui.enrollface.SignUpFaceFragment
import com.example.smartattendancecheckapp.presentation.ui.login.usrNum
import dagger.hilt.android.AndroidEntryPoint

var photoIndex: Int = 0
var USER_NUMBER = ""

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val permission = arrayOf(Manifest.permission.CAMERA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermission.launch(permission)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        val viewModel: SignUpViewModel by viewModels()

        binding.btnJoinLogin.setOnClickListener {

            // 입력 값을 모두 입력 했는지 확인
            if(binding.edtJoinStudentNum.text.toString() != "" && binding.edtJoinPassword.text.toString() != "" && binding.edtJoinStudentName.text.toString() != "") {

//                테스트용
                viewModel.requestTest()
                viewModel.signUpState.observe(this) {
                    when(viewModel.signUpState.value) {
                        SignUpState.SUCCESS -> {
                            Toast.makeText(this@SignUpActivity, "사용자 등록 성공", Toast.LENGTH_SHORT).show()

                            usrNum = binding.edtJoinStudentNum.toString()

                            binding.layoutSignUp.isVisible = false
                            USER_NUMBER = binding.edtJoinStudentNum.text.toString()

                            val bundle = Bundle()
                            bundle.putString("usrNum", binding.edtJoinStudentNum.text.toString())



                            val signUpFaceFragment = SignUpFaceFragment()
                            signUpFaceFragment.arguments = bundle

                            supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.layout_signup_face, signUpFaceFragment)
                                .commit()
                        }
                        SignUpState.FAIL -> {
                            Toast.makeText(this@SignUpActivity, "사용자 등록 실패", Toast.LENGTH_SHORT).show()
                        }
                        else -> {}
                    }
                }

//                실제 사용자 등록
//                viewModel.requestSignUp(
//                    SignUpData(
//                        binding.edtJoinStudentNum.text.toString(),
//                        binding.edtJoinStudentName.text.toString(),
//                        binding.edtJoinPassword.text.toString()
//                    )
//                )
//                viewModel.signUpState.observe(this) {
//                    when(viewModel.signUpState.value) {
//                        SignUpState.SUCCESS -> {
//                            Toast.makeText(this@SignUpActivity, "사용자 등록 성공", Toast.LENGTH_SHORT).show()
//
//                            usrNum = binding.edtJoinStudentNum.toString()
//
//                            binding.layoutSignUp.isVisible = false
//                            val bundle = Bundle()
//                            bundle.putString("usrNum", binding.edtJoinStudentNum.text.toString())
//
//                            val signUpFaceFragment = SignUpFaceFragment()
//                            signUpFaceFragment.arguments = bundle
//
//                            supportFragmentManager
//                                .beginTransaction()
//                                .replace(R.id.layout_signup_face, signUpFaceFragment)
//                                .commit()
//                        }
//                        SignUpState.FAIL -> {
//                            Toast.makeText(this@SignUpActivity, "사용자 등록 실패", Toast.LENGTH_SHORT).show()
//                        }
//                        else -> {}
//                    }
//                }
            }
            else {
                Toast.makeText(this@SignUpActivity, "아이디, 비밀번호 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }
    }



    // 권한을 허용하도록 요청
    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
        results.forEach {
            if(!it.value) {
                Toast.makeText(applicationContext, "권한 허용 필요", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}