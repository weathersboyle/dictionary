package io.intrepid.dictionary

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://od-api.oxforddictionaries.com/api/v1/"

interface DictionaryService {
    @GET("entries/{language}/{query}")
    fun getDefinition(@Path("language") language: String, @Path("query") query: String): Call<DefinitionResponse>
}

fun createDictionaryService(): DictionaryService {
    val httpClient: OkHttpClient = createOkHttpClientBuilder()
        .addInterceptor { chain ->
            val requestBuilder: Request.Builder = chain.request().newBuilder()
            requestBuilder.addHeader("Accept", "application/json")
            requestBuilder.addHeader("app_id", "a4cecc1b")
            requestBuilder.addHeader("app_key", "e3b54d1112481da360ccc16c5f6c3fc9")
            chain.proceed(requestBuilder.build())
        }
        .build()

    val gson: Gson = createGson()

    val retrofitClient: Retrofit = createRetrofitClient(BASE_URL, httpClient, gson)

    return retrofitClient.create(DictionaryService::class.java)
}
