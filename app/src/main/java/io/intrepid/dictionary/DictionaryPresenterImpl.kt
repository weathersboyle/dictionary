package io.intrepid.dictionary

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class DictionaryPresenterImpl(
    private val dictionaryView: DictionaryView,
    private val dictionaryService: DictionaryService
) : DictionaryPresenter {
    companion object {
        private const val SOURCE_LANGUAGE = "en"
    }

    override fun onDefineClick(word: CharSequence) {
        dictionaryService.getDefinition(SOURCE_LANGUAGE, word.toString().toLowerCase()).enqueue(
            object : Callback<DefinitionResponse> {
                override fun onFailure(call: Call<DefinitionResponse>, t: Throwable) {
                    Timber.e(t)
                }

                override fun onResponse(call: Call<DefinitionResponse>, response: Response<DefinitionResponse>) {
                    if (response.isSuccessful) {
                        val definition =
                            response.body()
                                ?.results?.firstOrNull()
                                ?.lexicalEntries?.firstOrNull()
                                ?.entries?.firstOrNull()
                                ?.senses?.firstOrNull()
                                ?.definitions?.firstOrNull()
                                ?: ""

                        updateDefinition(definition)
                    } else {
                        Timber.d("HttpError: ${response.code()}")
                    }
                }
            }
        )
    }

    private fun updateDefinition(definition: String) {
        dictionaryView.showDefinition(definition)
    }
}
