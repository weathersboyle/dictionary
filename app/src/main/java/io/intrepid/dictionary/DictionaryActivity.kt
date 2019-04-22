package io.intrepid.dictionary

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class DictionaryActivity : AppCompatActivity(), DictionaryView {
    private val dictionaryService: DictionaryService by lazy { createDictionaryService() }
    private lateinit var dictionaryPresenter: DictionaryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        dictionaryPresenter = DictionaryPresenterImpl(this, dictionaryService)

        defineButton.setOnClickListener { dictionaryPresenter.onDefineClick(queryText.text) }
    }

    override fun showDefinition(definition: String) {
        definitionText.text = definition
    }
}
