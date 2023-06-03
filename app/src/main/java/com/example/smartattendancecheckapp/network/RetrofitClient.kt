package com.example.smartattendancecheckapp.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

//    private const val BASEURL = "http://172.16.37.20:8080/"
//    private const val BASEURL = "http://172.16.39.133:8080/"

    private const val BASEURL = "http://192.168.137.30:8080"

//    private const val BASEURL: String = "https://api.chucknorris.io/"

    //    logger 생성
    private val logger : HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    //    OkHttpClient 생성
    private val okHttp : OkHttpClient.Builder = OkHttpClient.Builder()
        .addInterceptor(logger)
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASEURL)
        .addConverterFactory(GsonConverterFactory.create())
        //    okHttp 빌드
        .client(okHttp.build())
        .build()

    val retrofitService: RetrofitService = retrofit.create(RetrofitService::class.java)
}