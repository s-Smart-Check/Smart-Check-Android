package com.example.smartattendancecheckapp.ui.main2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.smartattendancecheckapp.R
import com.example.smartattendancecheckapp.databinding.ActivityMain2Binding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation_bottom_main2)
        bottomNavigationView.itemIconTintList = null            // BottomNavigation 에서 Theme의 색을 참조하던 것을 초기화 시킴


//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_init) as NavHostFragment
        val navController = supportFragmentManager.findFragmentById(R.id.nav_host_init)?.findNavController()
        navController?.let {
            bottomNavigationView.setupWithNavController(it)
        }
    }
}