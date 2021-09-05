package com.bestway.pronounceit.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.androiddevs.shoppinglisttestingyt.others.Resource
import com.bestway.pronounceit.data.local.Word
import com.bestway.pronounceit.data.local.WordDatabase
import com.bestway.pronounceit.data.remote.DictionaryAPI
import com.bestway.pronounceit.data.remote.responses.WordResponse
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(
    private val database: WordDatabase,
    private val api: DictionaryAPI
) : DictionaryRepository {
    override suspend fun searchForWord(word: String): Resource<WordResponse> {
        return try {
            val response = api.searchForWord(word)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Unknown error occurred", null)
            } else {
                Resource.error("Unknown error occurred", null)
            }
        } catch (e: Exception) {
            Log.e("word error", e.toString())
            e.printStackTrace()
            Resource.error("Check Internet connection", null)
        }
    }

    override suspend fun insertWord(word: String) {
        database.wordDao().insert(Word(word))
    }

    override fun getWordsHistory(): LiveData<List<Word>> =
        database.wordDao().getWords()

}