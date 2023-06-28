package com.example.smartattendancecheckapp.ui.signup

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.smartattendancecheckapp.R
import com.example.smartattendancecheckapp.databinding.ActivitySignupBinding
import com.example.smartattendancecheckapp.model.TestList
import com.example.smartattendancecheckapp.network.RetrofitClient.retrofitService
import retrofit2.Call
import retrofit2.Response
import com.example.smartattendancecheckapp.model.request.SignUpData
import com.example.smartattendancecheckapp.model.response.SignUpRes
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

var photoIndex: Int = 0

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private var photoMultiPartList = mutableListOf<MultipartBody.Part>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermission.launch(permission)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        binding.btnJoinLogin.setOnClickListener {

            sendImage(binding.edtJoinStudentNum.text.toString(), photoMultiPartList)

            // 입력 값을 모두 입력 했는지 확인
            if(binding.edtJoinStudentNum.text.toString() != "" && binding.edtJoinPassword.text.toString() != "" && binding.edtJoinStudentName.text.toString() != "") {

//                retrofitService.getTestList().enqueue(object : retrofit2.Callback<TestList> {
//                    override fun onResponse(call: Call<TestList>, response: Response<TestList>) {
////                      통신 성공
//                        Toast.makeText(this@SignUpActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()
////                        sendImage(binding.edtJoinStudentNum.text.toString(), photoMultiPartList)
//                        finish()
//                    }
//
//                    override fun onFailure(call: Call<TestList>, t: Throwable) {
////                      통신 실패
//                        Toast.makeText(this@SignUpActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
//                    }
//
//                })


                retrofitService.requestSignUp(
                    SignUpData(
                    binding.edtJoinStudentNum.text.toString(),
                    binding.edtJoinStudentName.text.toString(),
                    binding.edtJoinPassword.text.toString()
                )
                ).enqueue(object : retrofit2.Callback<SignUpRes> {
                    override fun onResponse(call: Call<SignUpRes>, response: Response<SignUpRes>) {
                        if(response.isSuccessful) {
                            when(response.code()) {
                                200 -> {
                                    Toast.makeText(this@SignUpActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()
                                    sendImage(binding.edtJoinStudentNum.text.toString(), photoMultiPartList)
                                    finish()
                                }
                                400 ->{
                                    Toast.makeText(this@SignUpActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                    override fun onFailure(call: Call<SignUpRes>, t: Throwable) {
                        Toast.makeText(this@SignUpActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
                    }
                })



            }
            else {
                Toast.makeText(this@SignUpActivity, "아이디, 비밀번호 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }

        // 사진 추가 버튼 클릭 시
        binding.btnJoinAddPhoto.setOnClickListener {
            pictureUri = createImageFile(binding.edtJoinStudentNum.text.toString())
            Log.d("사진 Uri", "$pictureUri")
            getTakePicture.launch(pictureUri)
        }
    }

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

    // 카메라를 실행한 후 찍은 사진을 저장
    var pictureUri: Uri? = null
    private val getTakePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        Log.d("zzz", "${pictureUri}")

        val file = File(absolutelyPath(pictureUri, this))
        Log.d("zzz", "$file")

        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("profile", file.name, requestFile)

        photoMultiPartList.add(body)
        Log.d("zzz", body.toString())


        if(it) {
            when(photoIndex) {
                1 -> pictureUri.let { binding.ivJoinPhoto1.setImageURI(pictureUri) }
                2 -> pictureUri.let { binding.ivJoinPhoto2.setImageURI(pictureUri) }
                3 -> pictureUri.let { binding.ivJoinPhoto3.setImageURI(pictureUri) }
                4 -> pictureUri.let { binding.ivJoinPhoto4.setImageURI(pictureUri) }
                5 -> pictureUri.let { binding.ivJoinPhoto5.setImageURI(pictureUri) }
            }
        }
    }

    // 절대경로 변환
    fun absolutelyPath(path: Uri?, context : Context): String {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        var result = c?.getString(index!!)

        return result!!
    }


    // 이미지 생성
    private fun createImageFile(studentNum: String): Uri? {
        val content = ContentValues().apply {
            photoIndex += 1
            Log.d("사진 생성", "${studentNum}_$photoIndex")
            put(MediaStore.Images.Media.DISPLAY_NAME, "${studentNum}_$photoIndex.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        }
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content)
    }

    // 이미지 서버로 전송
    fun sendImage(studentNum: String, body: List<MultipartBody.Part>) {
        retrofitService.sendImage(studentNum, body).enqueue(object: retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    Toast.makeText(this@SignUpActivity, "이미지 전송 성공", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@SignUpActivity, "이미지 전송 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("test", t.message.toString())
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("액티비티 실행 결과", "zzz")
    }
}