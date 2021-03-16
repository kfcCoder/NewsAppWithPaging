package com.example.newsappwithpaging.paging.breakingnews

import android.util.Log
import androidx.paging.PagingSource
import com.example.newsappwithpaging.TAG
import com.example.newsappwithpaging.api.NewsApi
import com.example.newsappwithpaging.api.RetrofitInstance
import com.example.newsappwithpaging.model.Article
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE = 1

class BreakingNewsPagingSource(
        private val countryCode: String
) : PagingSource<Int, Article>() {

    private val newsApi = RetrofitInstance.getApi

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val currentPage = params.key ?: STARTING_PAGE

        return try {
            val response = newsApi.getBreakingNews(
                    countryCode = countryCode,
                    page = currentPage)
            val articles = response.body()?.articles!!
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