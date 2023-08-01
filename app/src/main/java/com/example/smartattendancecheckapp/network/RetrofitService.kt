package com.example.smartattendancecheckapp.network

import com.example.smartattendancecheckapp.model.request.LoginData
import com.example.smartattendancecheckapp.model.request.SignUpData
import com.example.smartattendancecheckapp.model.request.StudentAttendanceData
import com.example.smartattendancecheckapp.model.response.LoginRes
import com.example.smartattendancecheckapp.model.response.SignUpRes
import com.example.smartattendancecheckapp.model.response.StudentAttendanceRes
import com.example.smartattendancecheckapp.model.TestList
import com.example.smartattendancecheckapp.model.request.AttendanceCalendar
import com.example.smartattendancecheckapp.model.response.AttendanceCalendarRes
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {
    @GET("jokes/categories")
    fun getTestList(): Call<TestList>

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

    @POST("checkPastAttend")
    fun getDateAttendance(
        @Body attendanceCalendar: AttendanceCalendar
    ): Call<AttendanceCalendarRes>

    // 다른 값도 같이 전송하는 경우
//    @Multipart
//    @POST("서버경로")
//    fun profileSend(
//        @Part("userId") userId: String,
//        @Part imageFile : MultipartBody.Part
//    ): Call<String>
}