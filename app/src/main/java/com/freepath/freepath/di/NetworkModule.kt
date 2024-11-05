package com.freepath.freepath.di

import com.freepath.freepath.BuildConfig
import com.freepath.freepath.data.plan.PlanService
import com.freepath.freepath.data.plan.RecommendService
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
import javax.inject.Qualifier

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
    @BaseRetrofit
    fun providesBaseRetrofit(): Retrofit {
        return Retrofit.Builder().client(getOkHttpClient()).baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    fun providesPlanService(@BaseRetrofit retrofit: Retrofit): PlanService {
        return retrofit.create(PlanService::class.java)
    }

    @Provides
    @AIRetrofit
    fun providesAIRetrofit(): Retrofit {
        return Retrofit.Builder().client(getOkHttpClient()).baseUrl(BASE_AI_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    fun providesAIService(@AIRetrofit retrofit: Retrofit): RecommendService {
        return retrofit.create(RecommendService::class.java)
    }

    private class HeaderInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
            proceed(request().newBuilder().addHeader("Authorization", API_KEY).build())
        }
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AIRetrofit
}

//private const val BASE_URL = BuildConfig.FREE_PATH_API_URL
private const val BASE_URL = "http://133.186.220.150/"
private const val BASE_AI_URL = "http://52.78.21.24:8000/"
private const val API_KEY = "API_KEY"