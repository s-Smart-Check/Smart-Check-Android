package com.example.smartattendancecheckapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.smartattendancecheckapp.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        binding.btnJoinLogin.setOnClickListener {
            if(binding.edtJoinId.text.toString() != "" && binding.edtJoinPassword.text.toString() != "") {
                Log.d("아이디 확인", binding.edtJoinId.text.toString())
                Toast.makeText(this@JoinActivity, "회원 가입 완료", Toast.LENGTH_SHORT).show()
                finish()
            }
            else {
                Toast.makeText(this@JoinActivity, "아이디, 비밀번호 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }
    }
}