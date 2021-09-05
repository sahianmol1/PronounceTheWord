package com.bestway.pronounceit.data.remote.responses

data class Definition(
    val definition: String,
    val example: String,
    val synonyms: List<String>,
    val antonyms: List<String>
)