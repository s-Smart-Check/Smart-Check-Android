package com.example.smartattendancecheckapp.ui.signup

import android.Manifest
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
import com.example.smartattendancecheckapp.R
import com.example.smartattendancecheckapp.databinding.FragmentEnrollFaceBinding
import com.example.smartattendancecheckapp.databinding.FragmentSignUpFaceBinding
import com.example.smartattendancecheckapp.network.RetrofitClient
import com.example.smartattendancecheckapp.ui.Login.usrNum
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File

var photoIndexSignUP: Int = 0

class SignUpFaceFragment : Fragment() {

    private lateinit var binding : FragmentSignUpFaceBinding
    private var photoMultiPartList = mutableListOf<MultipartBody.Part>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpFaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 사진 추가 버튼 클릭 시
        binding.btnCamera.setOnClickListener {
            pictureUri = createImageFile(usrNum)
            getTakePicture.launch(pictureUri)
        }

        binding.btnEnroll.setOnClickListener {
//            sendImage(usrNum, photoMultiPartList)
            requireActivity().onBackPressed()
        }
    }

    // 요청하고자 하는 권한들
    private val permission = arrayOf(Manifest.permission.CAMERA)

    // 권한을 허용하도록 요청
    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
        results.forEach {
            if(!it.value) {
                Toast.makeText(requireContext(), "권한 허용 필요", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            }
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
            when(photoIndexSignUP) {
                1 -> pictureUri.let { binding.ivSignupPhoto1.setImageURI(pictureUri) }
                2 -> pictureUri.let { binding.ivSignupPhoto2.setImageURI(pictureUri) }
                3 -> pictureUri.let { binding.ivSignupPhoto3.setImageURI(pictureUri) }
                4 -> pictureUri.let { binding.ivSignupPhoto4.setImageURI(pictureUri) }
                5 -> pictureUri.let { binding.ivSignupPhoto5.setImageURI(pictureUri) }
                6 -> pictureUri.let { binding.ivSignupPhoto6.setImageURI(pictureUri) }
            }
        }
    }

    // 절대경로 변환
    private fun absolutelyPath(path: Uri?, context : Context): String {
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
            photoIndexSignUP += 1
            put(MediaStore.Images.Media.DISPLAY_NAME, "${studentNum}_${photoIndexSignUP}.jpg")
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

            }

        })
    }
}