package com.nvkhang96.tpitaipeitravel.core.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nvkhang96.tpitaipeitravel.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

private const val TRAVEL_TAIPEI_API_BASE_URL = "https://www.travel.taipei/open-api/"

val coreNetworkModule = module {
    single<OkHttpClient> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val headerInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()

            val request = originalRequest.newBuilder()
                .header("Accept", "application/json")
                .build()

            chain.proceed(request)
        }

        val clientBuilder = OkHttpClient.Builder()

        clientBuilder.addInterceptor(headerInterceptor)
        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(loggingInterceptor)
        }

        clientBuilder.build()
    }

    single<Retrofit> {
        val networkJson = Json { ignoreUnknownKeys = true }
        Retrofit.Builder()
            .baseUrl(TRAVEL_TAIPEI_API_BASE_URL)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .client(get())
            .build()
    }
}