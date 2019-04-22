package io.intrepid.dictionary

import com.google.gson.annotations.SerializedName

data class DefinitionResponse(@SerializedName("results") val results: List<Result>)

data class Result(@SerializedName("lexicalEntries") val lexicalEntries: List<LexicalEntry>)

data class LexicalEntry(@SerializedName("entries") val entries: List<Entry>)

data class Entry(@SerializedName("senses") val senses: List<Sense>)

data class Sense(@SerializedName("definitions") val definitions: List<String>)
