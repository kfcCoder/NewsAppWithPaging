package com.example.newsappwithpaging.api

import com.example.newsappwithpaging.model.NewsResponse
import com.example.newsappwithpaging.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
            @Query("country") countryCode: String = "us",
            @Query("page") page: Int = 1,
            @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>


    @GET("v2/everything")
    suspend fun searchNews(
            @Query("q") query: String,
            //@Query("pageSize") perPage: Int = 100, // 每頁最多100條新聞(default 20條)
            @Query("page") page: Int = 1, // 顯示第1頁
            @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse


}