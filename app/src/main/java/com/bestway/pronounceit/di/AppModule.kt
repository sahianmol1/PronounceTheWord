package com.bestway.pronounceit.di

import android.content.Context
import androidx.room.Room
import com.bestway.pronounceit.data.local.WordDatabase
import com.bestway.pronounceit.data.remote.DictionaryAPI
import com.bestway.pronounceit.others.Constants.BASE_URL
import com.bestway.pronounceit.repositories.DictionaryRepository
import com.bestway.pronounceit.repositories.DictionaryRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideWordDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, WordDatabase::class.java, "word_db").build()

    @Provides
    @Singleton
    fun provideDao(database: WordDatabase) = database.wordDao()

    @Singleton
    @Provides
    fun providesDictionaryAPI(): DictionaryAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(DictionaryAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesDefaultShoppingRepository(database: WordDatabase, api: DictionaryAPI) =
        DictionaryRepositoryImpl(database, api) as DictionaryRepository
}