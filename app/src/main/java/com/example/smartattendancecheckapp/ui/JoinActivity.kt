package com.example.smartattendancecheckapp.ui

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.example.smartattendancecheckapp.R
import com.example.smartattendancecheckapp.databinding.ActivityJoinBinding
import com.example.smartattendancecheckapp.network.RetrofitClient.retrofitService
import com.example.smartattendancecheckapp.model.testList
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class JoinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinBinding

    // 파일 불러오기
//    private val getContentImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
//        uri.let { binding.mainImg.setImageURI(uri) }
//    }

    // 카메라를 실행한 후 찍은 사진을 저장
    var pictureUri: Uri? = null
    private val getTakePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        Log.d("zzz", "${pictureUri}")
        if(it) {
            pictureUri.let { binding.ivJoinPhoto.setImageURI(pictureUri) }
        }
    }

    // 카메라를 실행하며 결과로 비트맵 이미지를 얻음
//    private val getTakePicturePreview = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
//        bitmap.let { binding.ivJoinPhoto.setImageBitmap(bitmap) }
//    }

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermission.launch(permission)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        binding.btnJoinLogin.setOnClickListener {
            if(binding.edtJoinStudentNum.text.toString() != "" && binding.edtJoinPassword.text.toString() != "" && binding.edtJoinStudentName.text.toString() != "") {

                retrofitService.getTestList().enqueue(object : retrofit2.Callback<testList> {
                    override fun onResponse(call: Call<testList>, response: Response<testList>) {
//                      통신 성공
                        Toast.makeText(this@JoinActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()
                        finish()
                    }

                    override fun onFailure(call: Call<testList>, t: Throwable) {
//                      통신 실패
                        Toast.makeText(this@JoinActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
                    }

                })
            }
            else {
                Toast.makeText(this@JoinActivity, "아이디, 비밀번호 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnJoinAddPhoto.setOnClickListener {
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
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("액티비티 실행 결과", "zzz")
    }
}