package com.example.smartattendancecheckapp.network

import com.example.smartattendancecheckapp.model.request.LoginData
import com.example.smartattendancecheckapp.model.request.SignUpData
import com.example.smartattendancecheckapp.model.request.StudentAttendanceData
import com.example.smartattendancecheckapp.model.response.LoginRes
import com.example.smartattendancecheckapp.model.response.SignUpRes
import com.example.smartattendancecheckapp.model.response.StudentAttendanceRes
import com.example.smartattendancecheckapp.model.testList
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RetrofitService {
    @GET("jokes/categories")
    fun getTestList(): Call<testList>

    @POST("tempJoin")
    fun requestSignUp(
        @Body signupData : SignUpData
    ): Call<SignUpRes>

    @POST("tempLogin")
    fun requestLogin(
        @Body loginData: LoginData
    ): Call<LoginRes>

    @POST("checkAttend")
    fun requestAttendanceInfo(
        @Body usrNum: StudentAttendanceData
    ): Call<StudentAttendanceRes>

    @Multipart
    @POST("image")
    fun sendImage(
        @Part("studentNum") studentNum: String,
        @Part imageFile: List<MultipartBody.Part>
    ): Call<String>

    // 다른 값도 같이 전송하는 경우
//    @Multipart
//    @POST("서버경로")
//    fun profileSend(
//        @Part("userId") userId: String,
//        @Part imageFile : MultipartBody.Part
//    ): Call<String>
}