package io.intrepid.dictionary

interface DictionaryView {
    fun showDefinition(definition: String)
}

interface DictionaryPresenter {
    fun onDefineClick(word: CharSequence)
}
