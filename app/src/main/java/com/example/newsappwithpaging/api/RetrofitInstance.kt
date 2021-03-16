package com.example.newsappwithpaging.api

import com.example.newsappwithpaging.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val getApi by lazy {
        retrofit.create(NewsApi::class.java)
    }



}