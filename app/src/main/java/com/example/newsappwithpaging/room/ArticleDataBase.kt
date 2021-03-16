package com.example.newsappwithpaging.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsappwithpaging.model.Article

/**
 * Singleton但創立時需要傳入Context, 所以不能標成'object', 而是要用companion object實現之
 */

@Database(
        entities = [Article::class],
        version = 1
)

@TypeConverters(Converters::class)
abstract class ArticleDataBase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    companion object {
        @Volatile
        private var instance: ArticleDataBase? = null

        fun getDatabase(context: Context): ArticleDataBase {
            return instance ?: synchronized(this) {
                instance ?: createDataBase(context).also { instance = it}
            }
        }

        // @params: #Context instance
        private fun createDataBase(context: Context) =
            Room.databaseBuilder(
                    context.applicationContext,
                    ArticleDataBase::class.java,
                    "article_db"
            ).build()

    }

}