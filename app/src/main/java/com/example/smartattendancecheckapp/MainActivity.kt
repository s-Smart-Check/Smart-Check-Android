package com.example.smartattendancecheckapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.smartattendancecheckapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_init) as NavHostFragment
//        val navController = navHostFragment.navController

        // 회원 가입 버튼 클릭
        binding.btnJoin.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }

        // 로그인 버튼 클릭
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}