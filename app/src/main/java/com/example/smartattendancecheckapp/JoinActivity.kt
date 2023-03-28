package com.example.smartattendancecheckapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.smartattendancecheckapp.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        binding.btnJoinLogin.setOnClickListener {
            Toast.makeText(this@JoinActivity, "회원 가입 완료", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}