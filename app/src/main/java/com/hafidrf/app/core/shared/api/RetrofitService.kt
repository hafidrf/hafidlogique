package com.hafidrf.app.core.shared.api

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.hafidrf.app.BuildConfig
import java.util.concurrent.TimeUnit

object RetrofitServices {
    inline fun <reified I> endpointAPI(context: Context): I {
        return builder(context, BuildConfig.BASE_URL)
    }

    inline fun <reified I> builder(context: Context, api: String): I {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(api)
            .client(getOKHttp(context))
            .addConverterFactory(MoshiConverterFactory.create(moshi).withNullSerialization())
            .build()
        return retrofit.create(I::class.java)
    }

    fun getOKHttp(context: Context): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(getInterceptor(context))
            .addNetworkInterceptor(getInterceptor(context))
            .addInterceptor(getHTTPLoggingInterceptor())
            .connectTimeout(59, TimeUnit.SECONDS)
            .writeTimeout(59, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

    private fun getHTTPLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

    private fun getInterceptor(context: Context): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val request: Request = chain.request().newBuilder()
                .header("app-id", "638eb4f26c32e99c9ed6bf1a")
                .cacheControl(CacheControl.Builder().noCache().build())
                .build()
            chain.proceed(request)
        }
    }
}