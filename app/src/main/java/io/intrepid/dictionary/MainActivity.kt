package io.intrepid.dictionary

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber

class MainActivity : AppCompatActivity(), Callback<DefinitionResponse> {
    companion object {
        private const val BASE_URL = "https://od-api.oxforddictionaries.com/api/v1/"
        private const val SOURCE_LANGUAGE = "en"
    }

    private val dictionaryService: DictionaryService by lazy { createDictionaryService() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        defineButton.setOnClickListener { getDefinition(queryText.text) }
    }

    override fun onFailure(call: Call<DefinitionResponse>, t: Throwable) {
        Timber.e(t)
    }

    override fun onResponse(call: Call<DefinitionResponse>, response: Response<DefinitionResponse>) {
        val definition: String = response.body()!!.results[0].lexicalEntries[0].entries[0].senses[0].definitions[0]
        definitionText.text = definition
    }

    private fun getDefinition(query: CharSequence) {
        dictionaryService.getDefinition(SOURCE_LANGUAGE, query.toString().toLowerCase())
            .enqueue(this)
    }

    private fun createDictionaryService(): DictionaryService {
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
}
