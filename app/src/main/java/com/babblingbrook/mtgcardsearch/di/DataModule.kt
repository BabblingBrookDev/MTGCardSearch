package com.babblingbrook.mtgcardsearch.di

import androidx.room.Room
import com.babblingbrook.mtgcardsearch.data.local.AppDatabase
import com.babblingbrook.mtgcardsearch.data.remote.ApiCallAdapterFactory
import com.babblingbrook.mtgcardsearch.data.remote.ApiService
import com.babblingbrook.mtgcardsearch.data.remote.MultipleConverterFactory
import com.babblingbrook.mtgcardsearch.repository.Repository
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {
    single { OkHttpClient().newBuilder().build() }
    single {
        Retrofit.Builder()
            .addCallAdapterFactory(ApiCallAdapterFactory())
            .addConverterFactory(MultipleConverterFactory())
            .baseUrl("https://api.scryfall.com")
            .client(get())
            .build()
    }
    single { get<Retrofit>().create(ApiService::class.java) }
    single { Room.databaseBuilder(get(), AppDatabase::class.java, "Scryfall.db").build() }
    single { get<AppDatabase>().cardDao() }
    single { get<AppDatabase>().feedDao() }
    single {
        Repository(
            get(),
            get(),
            get()
        )
    }
}