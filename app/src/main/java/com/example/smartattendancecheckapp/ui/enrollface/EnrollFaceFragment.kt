package com.example.smartattendancecheckapp.ui.enrollface

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
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
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.smartattendancecheckapp.R
import com.example.smartattendancecheckapp.databinding.FragmentEnrollFaceBinding
import com.example.smartattendancecheckapp.network.RetrofitClient
import com.example.smartattendancecheckapp.ui.Login.usrNum
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File

var photoIndexEnroll: Int = 0

class EnrollFaceFragment : Fragment() {

    private lateinit var binding : FragmentEnrollFaceBinding
    private lateinit var navController: NavController
    private var photoMultiPartList = mutableListOf<MultipartBody.Part>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentEnrollFaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        // 사진 추가 버튼 클릭 시
        binding.btnCamera.setOnClickListener {
            pictureUri = createImageFile(usrNum)
            Log.d("사진 Uri", "$pictureUri")
            getTakePicture.launch(pictureUri)
        }

        binding.btnEnroll.setOnClickListener {
            sendImage(usrNum, photoMultiPartList)
            navController.navigate(R.id.action_navigation_enroll_to_navigation_attend_check)

        }
    }

    // 카메라를 실행한 후 찍은 사진을 저장
    var pictureUri: Uri? = null
    private val getTakePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) {

        val file = File(absolutelyPath(pictureUri, requireContext()))

        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("profile", file.name, requestFile)

        photoMultiPartList.add(body)

        if(it) {
            when(photoIndexEnroll) {
                1 -> pictureUri.let { binding.ivEnrollPhoto1.setImageURI(pictureUri) }
                2 -> pictureUri.let { binding.ivEnrollPhoto2.setImageURI(pictureUri) }
                3 -> pictureUri.let { binding.ivEnrollPhoto3.setImageURI(pictureUri) }
                4 -> pictureUri.let { binding.ivEnrollPhoto4.setImageURI(pictureUri) }
                5 -> pictureUri.let { binding.ivEnrollPhoto5.setImageURI(pictureUri) }
                6 -> pictureUri.let { binding.ivEnrollPhoto6.setImageURI(pictureUri) }
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
            photoIndexEnroll += 1
            Log.d("사진 생성", "${studentNum}_${photoIndexEnroll}")
            put(MediaStore.Images.Media.DISPLAY_NAME, "${studentNum}_${photoIndexEnroll}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        }
        return requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content)
    }

    // 이미지 서버로 전송
    fun sendImage(studentNum: String, body: List<MultipartBody.Part>) {
        RetrofitClient.retrofitService.sendImage(studentNum, body).enqueue(object: retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    Toast.makeText(requireContext(), "이미지 전송 성공", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), "이미지 전송 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("test", t.message.toString())
            }

        })
    }

}