package com.bestway.pronounceit.data.remote

import com.bestway.pronounceit.data.remote.responses.WordResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryAPI {

    @GET("{word}")
    suspend fun searchForWord(
        @Path("word") word: String
    ): Response<WordResponse>
}