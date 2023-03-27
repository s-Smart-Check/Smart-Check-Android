package com.example.smartattendancecheckapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.smartattendancecheckapp.databinding.ActivityLoginBinding

private lateinit var binding: ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        // 로그인 버튼 클릭 시
        binding.btnLoginLogin.setOnClickListener {

            // TODO( "로그인 확인 하고" )
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }
}