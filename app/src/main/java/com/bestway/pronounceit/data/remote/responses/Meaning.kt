package com.bestway.pronounceit.data.remote.responses

data class Meaning(
    val definitions: List<Definition>,
    val partOfSpeech: String
)