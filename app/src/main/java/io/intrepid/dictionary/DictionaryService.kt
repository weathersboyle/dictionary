package io.intrepid.dictionary

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryService {
    @GET("entries/{language}/{query}")
    fun getDefinition(@Path("language") language: String, @Path("query") query: String): Call<DefinitionResponse>
}
