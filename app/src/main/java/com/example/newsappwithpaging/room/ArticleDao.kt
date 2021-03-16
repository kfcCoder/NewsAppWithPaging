package com.example.newsappwithpaging.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsappwithpaging.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticlesLive(): LiveData<List<Article>>

    @Delete
    suspend fun delete(article: Article)
}