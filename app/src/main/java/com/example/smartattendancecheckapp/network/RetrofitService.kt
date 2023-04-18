package com.example.smartattendancecheckapp.network

import com.example.smartattendancecheckapp.model.testList
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {
    @GET("jokes/categories")
    fun getTestList(): Call<testList>
}