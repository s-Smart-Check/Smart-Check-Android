package com.example.smartattendancecheckapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.smartattendancecheckapp.R
import com.example.smartattendancecheckapp.databinding.ActivityLoginBinding
import com.example.smartattendancecheckapp.model.request.LoginData
import com.example.smartattendancecheckapp.model.response.LoginRes
import com.example.smartattendancecheckapp.network.RetrofitClient.retrofitService
import com.example.smartattendancecheckapp.model.testList
import retrofit2.Call
import retrofit2.Response

lateinit var usrNum : String
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        // 로그인 버튼 클릭 시
        binding.btnLoginLogin.setOnClickListener {
            // 입력 값이 있는 경우
            if (binding.edtLoginStudentNum.text.toString() != "" && binding.edtLoginPassword.text.toString() != "") {

//                retrofitService.getTestList().enqueue(object : retrofit2.Callback<testList> {
//                    // 정상적으로 응답이 온 경우
//                    override fun onResponse(call: Call<testList>, response: Response<testList>) {
//                        Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
//                        val intent = Intent(this@LoginActivity, MainActivity2::class.java)
//                        startActivity(intent)
//                    }
//                    // 통신에 실패한 경우
//                    override fun onFailure(call: Call<testList>, t: Throwable) {
//                        Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
//                    }
//                })

                retrofitService.requestLogin(LoginData(
                    binding.edtLoginStudentNum.text.toString(),
                    binding.edtLoginPassword.text.toString()
                )).enqueue(object : retrofit2.Callback<LoginRes> {
                    // 정상적으로 응답이 온 경우
                    override fun onResponse(call: Call<LoginRes>, response: Response<LoginRes>) {
                        if (response.isSuccessful){
                            when(response.code()) {
                                200 -> {
                                    usrNum = binding.edtLoginStudentNum.text.toString()
                                    Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this@LoginActivity, MainActivity2::class.java)
                                    startActivity(intent)
                                }
                                400 -> {
                                    if (response.message() == "비밀번호가 맞지 않습니다.")
                                        Toast.makeText(this@LoginActivity, "비번", Toast.LENGTH_SHORT).show()
                                    else if (response.message() == "존재하지 않는 아이디입니다.")
                                        Toast.makeText(this@LoginActivity, "아이디", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                    }
                    // 통신에 실패한 경우
                    override fun onFailure(call: Call<LoginRes>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                })


            }
            // 입력 값이 없는 경우
            else { Toast.makeText(this@LoginActivity, "아이디, 비밀번호 입력하세요", Toast.LENGTH_SHORT).show() }
        }
    }
}