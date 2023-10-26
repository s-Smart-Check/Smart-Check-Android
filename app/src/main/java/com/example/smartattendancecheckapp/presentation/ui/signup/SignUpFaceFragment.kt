package com.example.smartattendancecheckapp.presentation.ui.signup

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
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.smartattendancecheckapp.databinding.FragmentSignUpFaceBinding
import com.example.smartattendancecheckapp.presentation.ui.attendcheck.AttendCheckViewModel
import com.example.smartattendancecheckapp.presentation.ui.signup.adapter.PhotoAdapter
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File

var photoIndexSignUP: Int = 0

@AndroidEntryPoint
class SignUpFaceFragment : Fragment() {

    private lateinit var binding : FragmentSignUpFaceBinding
    private var photoMultiPartList = mutableListOf<MultipartBody.Part>()
    private lateinit var viewModel: SignUpFaceViewModel
    private var pictureUri: Uri? = null
    private val photoAdapter = PhotoAdapter()

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

        val receivedValue1 = arguments?.getString("usrNum")
        viewModel = ViewModelProvider(this)[SignUpFaceViewModel::class.java]

        binding.rvPhotos.adapter = photoAdapter
        binding.rvPhotos.layoutManager = GridLayoutManager(requireContext(), 3)

        // 사진 추가 버튼 클릭 시
        binding.btnCamera.setOnClickListener {
            pictureUri = receivedValue1?.let { it1 -> createImageFile(it1) }
            getTakePicture.launch(pictureUri)
        }

        binding.btnEnroll.setOnClickListener {
            if (receivedValue1 != null) {
                sendImage(receivedValue1, photoMultiPartList)
            }
            requireActivity().onBackPressed()
        }
    }

    // 카메라를 실행한 후 찍은 사진을 저장
    private val getTakePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) {

        val file = File(absolutelyPath(pictureUri, requireContext()))
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("profile", file.name, requestFile)

        photoMultiPartList.add(body)
        viewModel.addUploadPhoto(pictureUri)
        viewModel.uploadPhotoList.observe(viewLifecycleOwner) {
            Log.d("이미지 리스트", "$it")
            photoAdapter.submitList(it.toList())
        }

//        if(it) {
//            when(photoIndexSignUP) {
//                1 -> pictureUri.let { binding.ivSignupPhoto1.setImageURI(pictureUri) }
//                2 -> pictureUri.let { binding.ivSignupPhoto2.setImageURI(pictureUri) }
//                3 -> pictureUri.let { binding.ivSignupPhoto3.setImageURI(pictureUri) }
//                4 -> pictureUri.let { binding.ivSignupPhoto4.setImageURI(pictureUri) }
//                5 -> pictureUri.let { binding.ivSignupPhoto5.setImageURI(pictureUri) }
//                6 -> pictureUri.let { binding.ivSignupPhoto6.setImageURI(pictureUri) }
//            }
//        }
    }

    // 절대경로 변환
    private fun absolutelyPath(path: Uri?, context : Context): String {
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        val index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        val result = c?.getString(index!!)

        return result!!
    }


    // 이미지 생성
    private fun createImageFile(studentNum: String): Uri? {
        val content = ContentValues().apply {
            photoIndexSignUP += 1
            put(MediaStore.Images.Media.DISPLAY_NAME, "${studentNum}_$photoIndexSignUP.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        }
        return requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content)
    }

    // 이미지 서버로 전송
    private fun sendImage(studentNum: String, imageFiles: List<MultipartBody.Part>) {
        viewModel.requestEnrollFace(studentNum, imageFiles)
        viewModel.enrollFaceState.observe(viewLifecycleOwner) { state ->
            when(state) {
                EnrollFaceState.SUCCESS -> {
                    Toast.makeText(requireContext(), "얼굴 등록 성공", Toast.LENGTH_SHORT).show()
                }
                EnrollFaceState.FAIL -> {
                    Toast.makeText(requireContext(), "얼굴 등록 실패", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}