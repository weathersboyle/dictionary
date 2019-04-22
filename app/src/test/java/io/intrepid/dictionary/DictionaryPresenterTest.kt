package io.intrepid.dictionary

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DictionaryPresenterTest {
    private val definitionResponse =
        DefinitionResponse(listOf(Result(listOf(LexicalEntry(listOf(Entry(listOf(Sense(listOf("Definition"))))))))))

    private val dictionaryView = mockk<DictionaryView>(relaxed = true)
    private val dictionaryService = mockk<DictionaryService>()
    private val dictionaryPresenter: DictionaryPresenter = DictionaryPresenterImpl(dictionaryView, dictionaryService)

    @Test
    fun `Get definition with a success calls back to the presenter and shows definition in the view`() {
        val definitionCall = mockk<Call<DefinitionResponse>>()
        val definitionCallback = slot<Callback<DefinitionResponse>>()
        every { dictionaryService.getDefinition(any(), eq("word")) } returns definitionCall
        every { definitionCall.enqueue(capture(definitionCallback)) } answers {
            definitionCallback.captured.onResponse(definitionCall, Response.success(definitionResponse))
        }

        dictionaryPresenter.onDefineClick("word")

        verify { dictionaryView.showDefinition("Definition") }
    }
}
