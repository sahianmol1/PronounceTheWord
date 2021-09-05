package com.bestway.pronounceit.repositories

import androidx.lifecycle.LiveData
import com.androiddevs.shoppinglisttestingyt.others.Resource
import com.bestway.pronounceit.data.local.Word
import com.bestway.pronounceit.data.remote.responses.WordResponse


interface DictionaryRepository {
    suspend fun searchForWord(word: String): Resource<WordResponse>

    suspend fun insertWord(word: String)

    fun getWordsHistory(): LiveData<List<Word>>
}