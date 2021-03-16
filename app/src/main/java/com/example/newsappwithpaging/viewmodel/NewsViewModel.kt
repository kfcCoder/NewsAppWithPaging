package com.example.newsappwithpaging.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.*
import com.example.newsappwithpaging.model.Article
import com.example.newsappwithpaging.repository.NewsRepository
import com.example.newsappwithpaging.room.ArticleDataBase
import kotlinx.coroutines.launch


class NewsViewModel(application: Application) : AndroidViewModel(application) {
    /** get some instances */
    private val articleDao = ArticleDataBase.getDatabase(application).getArticleDao()

    private val repository = NewsRepository.getInstance(articleDao)

    /** Retrofit operation */
    // BreakingNews(PagingSource會自動呼叫此函數)
    val breakingNewsLive = repository.getgetBreakingNewsPaged("us").cachedIn(viewModelScope)

    // SearchNews
    private val searchNewsQuery = MutableLiveData<String>()

    fun setSearchNewsQuery(query: String) {
        searchNewsQuery.value = query
    }

    val searchedNewsLive = Transformations.switchMap(searchNewsQuery) {
        repository.getSearchedNews(it).cachedIn(viewModelScope)
    }

    /** Room operation */
    fun upsert(article: Article) {
        viewModelScope.launch {
            repository.upsert(article)
        }
    }

    fun delete(article: Article) {
        viewModelScope.launch {
            repository.delete(article)
        }
    }

    fun getSavedNewsLive() = repository.getSavedNewsLive()


}