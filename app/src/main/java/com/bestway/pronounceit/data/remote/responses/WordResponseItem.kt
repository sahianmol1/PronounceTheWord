package com.bestway.pronounceit.data.remote.responses

data class WordResponseItem(
    val meanings: List<Meaning>,
    val phonetics: List<Phonetic>,
    val word: String
)