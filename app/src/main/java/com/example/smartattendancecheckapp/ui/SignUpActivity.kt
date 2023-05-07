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
import com.example.smartattendancecheckapp.databinding.ActivitySignupBinding
import com.example.smartattendancecheckapp.model.request.SignUpData
import com.example.smartattendancecheckapp.model.response.SignUpRes
import com.example.smartattendancecheckapp.network.RetrofitClient.retrofitService
import com.example.smartattendancecheckapp.model.testList
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

var photoIndex: Int = 0

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    // 파일 불러오기
//    private val getContentImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
//        uri.let { binding.mainImg.setImageURI(uri) }
//    }

    // 카메라를 실행한 후 찍은 사진을 저장
    var pictureUri: Uri? = null
    private val getTakePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        Log.d("zzz", "${pictureUri}")
        if(it) {
            when(photoIndex) {
                1 -> pictureUri.let { binding.ivJoinPhoto1.setImageURI(pictureUri) }
                2 -> pictureUri.let { binding.ivJoinPhoto2.setImageURI(pictureUri) }
                3 -> pictureUri.let { binding.ivJoinPhoto3.setImageURI(pictureUri) }
            }
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

        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        binding.btnJoinLogin.setOnClickListener {
            // 입력 값을 모두 입력했는지 확인
            if(binding.edtJoinStudentNum.text.toString() != "" && binding.edtJoinPassword.text.toString() != "" && binding.edtJoinStudentName.text.toString() != "") {

                retrofitService.getTestList().enqueue(object : retrofit2.Callback<testList> {
                    override fun onResponse(call: Call<testList>, response: Response<testList>) {
//                      통신 성공
                        Toast.makeText(this@SignUpActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()
                        finish()
                    }

                    override fun onFailure(call: Call<testList>, t: Throwable) {
//                      통신 실패
                        Toast.makeText(this@SignUpActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
                    }

                })

//                TODO("서버 구현 되면 이거 사용")
//                retrofitService.requestSignUp(SignUpData(
//                    binding.tvJoinStudentNum.text.toString(),
//                    binding.tvJoinStudentName.text.toString(),
//                    binding.tvJoinPassword.text.toString()
//                )).enqueue(object : retrofit2.Callback<SignUpRes> {
//                    override fun onResponse(call: Call<SignUpRes>, response: Response<SignUpRes>) {
////                      통신 성공
//                        Toast.makeText(this@SignUpActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()
//                        finish()
//                    }
//
//                    override fun onFailure(call: Call<SignUpRes>, t: Throwable) {
////                      통신 실패
//                        Toast.makeText(this@SignUpActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
//                    }
//
//                })


            }
            else {
                Toast.makeText(this@SignUpActivity, "아이디, 비밀번호 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnJoinAddPhoto.setOnClickListener {
            pictureUri = createImageFile(binding.edtJoinStudentNum.text.toString())
            getTakePicture.launch(pictureUri)
        }
    }

    private fun createImageFile(studentNum: String): Uri? {
        val content = ContentValues().apply {
            photoIndex += 1
            Log.d("사진 생성", "${studentNum}_${photoIndex}")
            put(MediaStore.Images.Media.DISPLAY_NAME, "${studentNum}_${photoIndex}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        }
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("액티비티 실행 결과", "zzz")
    }
}