package com.example.newsappwithpaging.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.newsappwithpaging.api.RetrofitInstance
import com.example.newsappwithpaging.model.Article
import com.example.newsappwithpaging.paging.breakingnews.BreakingNewsPagingSource
import com.example.newsappwithpaging.paging.searchnews.SearchNewsPagingSource

import com.example.newsappwithpaging.room.ArticleDao

/**
 * Singleton: pass in #ArticleDao instance to create #Repository object
 */
class NewsRepository private constructor(
        private val dao: ArticleDao
) {

    /** Retrofit operation */
    fun getgetBreakingNewsPaged(countryCode: String): LiveData<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { BreakingNewsPagingSource(countryCode) }
        ).liveData
    }

    fun getSearchedNews(searchQuery: String): LiveData<PagingData<Article>> {
        return Pager(
                config = PagingConfig(
                        pageSize = 20,
                        maxSize = 100,
                        enablePlaceholders = false
                ),
                pagingSourceFactory = { SearchNewsPagingSource(searchQuery) }
        ).liveData
    }


    /** Room operation */
    suspend fun upsert(article: Article) = dao.upsert(article)

    suspend fun delete(article: Article) = dao.delete(article)

    fun getSavedNewsLive() = dao.getAllArticlesLive()


    /** get singleton instance */
    companion object {
        @Volatile
        private var INSTANCE: NewsRepository? = null

        // @params: #ArticleDao instance
        fun getInstance(dao: ArticleDao): NewsRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: NewsRepository(dao).also { INSTANCE = it }
            }
        }
    }

}