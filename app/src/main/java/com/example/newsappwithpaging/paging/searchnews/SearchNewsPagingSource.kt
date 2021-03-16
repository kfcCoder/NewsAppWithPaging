package com.example.newsappwithpaging.paging.searchnews

import android.util.Log
import androidx.paging.PagingSource
import com.example.newsappwithpaging.TAG
import com.example.newsappwithpaging.api.NewsApi
import com.example.newsappwithpaging.api.RetrofitInstance
import com.example.newsappwithpaging.model.Article
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE = 1

class SearchNewsPagingSource(
        private val searchQuery: String
) : PagingSource<Int, Article>() {

    private val newsApi = RetrofitInstance.getApi

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {

        return try {
            val currentPage = params.key ?: STARTING_PAGE

            val response = newsApi.searchNews(
                    query = searchQuery,
                    page = currentPage)

            val articles = response.articles

            // 请求失败或者出现异常，会跳转到 case 语句返回 LoadResult.Error(e)
            // 请求成功，构造一个 LoadResult.Page 返回
            LoadResult.Page(
                    data = articles,
                    prevKey = if(currentPage == STARTING_PAGE) null else currentPage - 1,
                    nextKey = if (articles.isEmpty()) null else currentPage + 1
            )


        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}