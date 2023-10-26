package com.example.smartattendancecheckapp.app.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

//    private const val BASEURL = "http://172.16.37.20:8080/"
//    private const val BASEURL = "http://172.16.39.133:8080/"
//    private const val BASEURL = "http://172.16.38.202:8080/"
//    private const val BASEURL = "http://192.168.137.109:8080/"
//    private const val BASEURL = "http://192.168.137.29:8080"
//    private const val BASEURL = "http://192.168.137.100:8080/"
//    private const val BASEURL = "http://172.16.38.213:8080/"

    private const val BASEURL: String = "https://api.chucknorris.io/"

    @Singleton
    @Provides
    fun provideHttpClient(
        logger: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(logger)
            .build()
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}