package com.aprendiendo.themovie.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {
    private const val BASE_URL = "http://api.themoviedb.org/3/"
    var retrofit : Retrofit? = null
    get() {
        if (field == null){
            val client = OkHttpClient.Builder()
            val loggingInterceptor = HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            client.addInterceptor(loggingInterceptor)
            field = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client.build())
                    .addConverterFactory(GsonConverterFactory.create()).build()
        }
            return field
    }
}