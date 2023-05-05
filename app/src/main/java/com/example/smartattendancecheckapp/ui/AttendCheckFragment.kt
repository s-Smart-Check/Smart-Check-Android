package com.example.smartattendancecheckapp.ui

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.smartattendancecheckapp.databinding.FragmentAttendCheckBinding
import java.text.SimpleDateFormat
import java.util.*

class AttendCheckFragment : Fragment() {

    private lateinit var binding : FragmentAttendCheckBinding

    // 카메라를 실행한 후 찍은 사진을 저장
    var pictureUri: Uri? = null
    private val getTakePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) {

    }

    // 요청하고자 하는 권한들
    private val permission = arrayOf(Manifest.permission.CAMERA)

    // 권한을 허용하도록 요청
    private val requestMultiplePermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
        results.forEach {
            if(!it.value) {
                Toast.makeText(context, "권한 허용 필요", Toast.LENGTH_SHORT).show()
            }
        }
    }

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

        requestMultiplePermission.launch(permission)

        binding.attendCheckBtnAddFace.setOnClickListener {
            pictureUri = createImageFile()
            getTakePicture.launch(pictureUri)
        }

    }

    private fun createImageFile(): Uri? {
        val now = SimpleDateFormat("yyMMdd_HHmmss").format(Date())
        val content = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "img_$now.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        }
        return requireActivity().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content)
    }

}