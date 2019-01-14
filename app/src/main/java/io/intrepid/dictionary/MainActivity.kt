package io.intrepid.dictionary

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        defineButton.setOnClickListener { getDefinition(queryText.text) }
    }

    private fun getDefinition(query: CharSequence) {
        // TODO get definition
    }
}
