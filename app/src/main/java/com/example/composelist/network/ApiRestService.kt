package com.example.composelist.network

import android.content.Context
import com.example.composelist.data.remote.model.response.userData.UserDataResponse
import com.example.composelist.network.interceptor.HeaderInterceptor
import com.example.composelist.network.interceptor.NetworkInterceptor
import com.example.composelist.util.Constant.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * Created by Jeetesh Surana.
 */

interface ApiRestService {

    @GET("users")
    suspend fun getUserData(@Query("page") page:String?): Response<UserDataResponse>


    companion object {
        operator fun invoke(
            context: Context,
            networkInterceptor: NetworkInterceptor,
            headerInterceptor: HeaderInterceptor
        ): ApiRestService {

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val cacheSize = 20 * 1024 * 1024L // 20 MB

            fun provideCache(): Cache? {
                var cache: Cache? = null
                try {
                    cache = Cache(File(context.cacheDir, "Cache_directory"), cacheSize)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return cache
            }

            val okHttpBuilder = OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .cache(provideCache())
                .addInterceptor(networkInterceptor)
                .addInterceptor(logging)
                .addInterceptor(headerInterceptor)


            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpBuilder.build())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRestService::class.java)
        }
    }
}
