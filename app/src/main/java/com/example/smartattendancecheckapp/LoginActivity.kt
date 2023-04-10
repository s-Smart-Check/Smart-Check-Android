package com.example.smartattendancecheckapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.smartattendancecheckapp.databinding.ActivityLoginBinding
import com.example.smartattendancecheckapp.network.RetrofitClient
import com.example.smartattendancecheckapp.network.RetrofitClient.retrofitService
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        // 로그인 버튼 클릭 시
        binding.btnLoginLogin.setOnClickListener {
            // 입력 값이 있는 경우
            if (binding.edtLoginId.text.toString() != "" && binding.edtLoginPassword.text.toString() != "") {

                retrofitService.getTestList().enqueue(object : retrofit2.Callback<testList> {
                    // 정상적으로 응답이 온 경우
                    override fun onResponse(call: Call<testList>, response: Response<testList>) {
                        Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, MainActivity2::class.java)
                        startActivity(intent)
                    }
                    // 통신에 실패한 경우
                    override fun onFailure(call: Call<testList>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                })

            }
            // 입력 값이 없는 경우
            else { Toast.makeText(this@LoginActivity, "아이디, 비밀번호 입력하세요", Toast.LENGTH_SHORT).show() }
        }
    }
}