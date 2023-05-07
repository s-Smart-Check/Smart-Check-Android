package com.example.smartattendancecheckapp.network

import com.example.smartattendancecheckapp.model.request.LoginData
import com.example.smartattendancecheckapp.model.request.SignUpData
import com.example.smartattendancecheckapp.model.response.LoginRes
import com.example.smartattendancecheckapp.model.response.SignUpRes
import com.example.smartattendancecheckapp.model.testList
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {
    @GET("jokes/categories")
    fun getTestList(): Call<testList>

    @POST("")
    fun requestSignUp(
        @Body signupData : SignUpData
    ): Call<SignUpRes>

    @POST("")
    fun requestLogin(
        @Body loginData: LoginData
    ): Call<LoginRes>
}