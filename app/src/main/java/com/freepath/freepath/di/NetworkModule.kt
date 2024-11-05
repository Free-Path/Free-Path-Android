package com.freepath.freepath.di

import com.freepath.freepath.BuildConfig
import com.freepath.freepath.data.plan.PlanService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private fun getOkHttpClient(): OkHttpClient = if (BuildConfig.DEBUG) {
        OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        )
    } else {
        OkHttpClient.Builder()
    }
        .addNetworkInterceptor(HeaderInterceptor())
        .build()

    @Provides
    fun providesAuthRetrofit(): Retrofit {
        return Retrofit.Builder().client(getOkHttpClient()).baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    fun providesPlanService(retrofit: Retrofit): PlanService {
        return retrofit.create(PlanService::class.java)
    }

    private class HeaderInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
            proceed(request().newBuilder().addHeader("Authorization", API_KEY).build())
        }
    }
}

//private const val BASE_URL = BuildConfig.FREE_PATH_API_URL
private const val BASE_URL = "http://133.186.220.150"
private const val API_KEY = "API_KEY"