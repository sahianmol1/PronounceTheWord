package com.bestway.pronounceit.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.shoppinglisttestingyt.others.Event
import com.androiddevs.shoppinglisttestingyt.others.Resource
import com.bestway.pronounceit.data.remote.responses.WordResponse
import com.bestway.pronounceit.repositories.DictionaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val repository: DictionaryRepository
) : ViewModel() {

    private val _wordResponse = MutableLiveData<Event<Resource<WordResponse>>>()
    val wordResponse: LiveData<Event<Resource<WordResponse>>> = _wordResponse

    fun searchForWord(word: String) {
        if (word.isEmpty()) return
        _wordResponse.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.searchForWord(word)
            _wordResponse.value = Event(response)
        }
    }

    fun insertWord(word: String) = viewModelScope.launch {
        repository.insertWord(word)
    }

    fun getWordsHistory() = repository.getWordsHistory()
}