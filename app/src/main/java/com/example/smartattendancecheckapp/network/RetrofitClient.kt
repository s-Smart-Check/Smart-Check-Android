package com.example.smartattendancecheckapp.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASEURL = "https://api.chucknorris.io/"

    //    logger 생성
    private val logger : HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    //    OkHttpClient 생성
    private val okHttp : OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor(logger)

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASEURL)
        .addConverterFactory(GsonConverterFactory.create())
        //    okHttp 빌드
        .client(okHttp.build())
        .build()

    val retrofitService: RetrofitService = retrofit.create(RetrofitService::class.java)
}