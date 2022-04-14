package com.eyepax.eyepaxtest.data.remote

import android.util.Log
import com.eyepax.eyepaxtest.BuildConfig
import com.eyepax.eyepaxtest.utils.BASE_URL
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


object RetrofitClient {

    private const val TAG = "<<-RetrofitClient->>"

    fun getClient(): NewsApiInterface {

        HttpLoggingInterceptor { message ->
            if (BuildConfig.DEBUG) {
                Log.e(TAG, message)
            }
        }

        val requestInterceptor = Interceptor { chain ->

            val url: HttpUrl = chain.request()
                .url
                .newBuilder()
                .build()

            val request: Request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            return@Interceptor chain.proceed(request)
        }

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiInterface::class.java)
    }
}