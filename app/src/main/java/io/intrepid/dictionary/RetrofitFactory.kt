package io.intrepid.dictionary

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

fun createOkHttpClientBuilder(): OkHttpClient.Builder =
    OkHttpClient.Builder()
        .addNetworkInterceptor { chain ->
            @Suppress("ConstantConditionIf")
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor {
                    Timber.tag("HTTP")
                    Timber.d(it)
                }.apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                loggingInterceptor.intercept(chain)
            } else {
                chain.proceed(chain.request())
            }
        }
        .readTimeout(60_000, TimeUnit.MILLISECONDS)

fun createGson(): Gson = GsonBuilder().create()

fun createRetrofitClient(baseUrl: String, okHttpClient: OkHttpClient, gson: Gson): Retrofit =
    Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
