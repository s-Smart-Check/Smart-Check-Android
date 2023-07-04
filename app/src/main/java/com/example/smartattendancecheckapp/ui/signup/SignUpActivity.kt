package com.example.smartattendancecheckapp.ui.signup

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.smartattendancecheckapp.R
import com.example.smartattendancecheckapp.databinding.ActivitySignupBinding
import com.example.smartattendancecheckapp.model.TestList
import com.example.smartattendancecheckapp.network.RetrofitClient.retrofitService
import retrofit2.Call
import retrofit2.Response
import com.example.smartattendancecheckapp.model.request.SignUpData
import com.example.smartattendancecheckapp.model.response.SignUpRes
import com.example.smartattendancecheckapp.ui.Login.usrNum
import com.example.smartattendancecheckapp.ui.enrollface.EnrollFaceFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

var photoIndex: Int = 0

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private var photoMultiPartList = mutableListOf<MultipartBody.Part>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermission.launch(permission)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        binding.btnJoinLogin.setOnClickListener {

            // 입력 값을 모두 입력 했는지 확인
            if(binding.edtJoinStudentNum.text.toString() != "" && binding.edtJoinPassword.text.toString() != "" && binding.edtJoinStudentName.text.toString() != "") {

//                retrofitService.getTestList().enqueue(object : retrofit2.Callback<TestList> {
//                    override fun onResponse(call: Call<TestList>, response: Response<TestList>) {
////                      통신 성공
//                        Toast.makeText(this@SignUpActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()
////                        sendImage(binding.edtJoinStudentNum.text.toString(), photoMultiPartList)
//                        finish()
//                    }
//
//                    override fun onFailure(call: Call<TestList>, t: Throwable) {
////                      통신 실패
//                        Toast.makeText(this@SignUpActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
//                    }
//
//                })


                retrofitService.requestSignUp(
                    SignUpData(
                    binding.edtJoinStudentNum.text.toString(),
                    binding.edtJoinStudentName.text.toString(),
                    binding.edtJoinPassword.text.toString()
                )
                ).enqueue(object : retrofit2.Callback<SignUpRes> {
                    override fun onResponse(call: Call<SignUpRes>, response: Response<SignUpRes>) {
                        if(response.isSuccessful) {
                            when(response.code()) {
                                200 -> {
                                    Toast.makeText(this@SignUpActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                                400 ->{
                                    Toast.makeText(this@SignUpActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                    override fun onFailure(call: Call<SignUpRes>, t: Throwable) {
                        Toast.makeText(this@SignUpActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            else {
                Toast.makeText(this@SignUpActivity, "아이디, 비밀번호 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }

        // 사진 추가 버튼 클릭 시
        binding.btnJoinAddPhoto.setOnClickListener {
            usrNum = binding.edtJoinStudentNum.toString()

            binding.layoutSignUp.isVisible = false
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.layout_signup_face, SignUpFaceFragment())
                .commit()
        }
    }

    // 요청하고자 하는 권한들
    private val permission = arrayOf(Manifest.permission.CAMERA)

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