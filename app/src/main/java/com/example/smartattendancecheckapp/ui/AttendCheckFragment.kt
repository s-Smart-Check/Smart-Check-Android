package com.example.smartattendancecheckapp.ui

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.smartattendancecheckapp.databinding.FragmentAttendCheckBinding

const val REQUEST_IMAGE_IMAGE_CAPTURE = 1
private val REQUEST_CAMERA_PERMISSION = 1

class AttendCheckFragment : Fragment() {

    private lateinit var binding : FragmentAttendCheckBinding

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

        binding.attendCheckBtnAddFace.setOnClickListener {
            checkCameraPermission()
        }

    }
    
    // 카메라 권한이 있는지 확인하고, 없으면 권한 요청
    private fun checkCameraPermission() {
        // 카메라 권한이 있는지 확인
        if (context?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.CAMERA) }
            != PackageManager.PERMISSION_GRANTED) {
            // 권한이 없으면 권한 요청
            ActivityCompat.requestPermissions(context as Activity,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_PERMISSION)
        }
        else {
            // 권한이 있으면 카메라 실행
            openCamera()
        }
    }


    // 카메라 실행
    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "카메라 왜 안됌??", Toast.LENGTH_SHORT).show()
        }
    }



}