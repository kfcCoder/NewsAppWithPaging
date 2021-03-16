package com.example.newsappwithpaging.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsappwithpaging.R
import com.example.newsappwithpaging.model.Article
import kotlinx.android.synthetic.main.itemview_article.view.*


class NewsAdapter : ListAdapter<Article, NewsAdapter.ArticleViewHolder>(DiffCallback) {
    /** handle click */
    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val article = getItem(absoluteAdapterPosition)
                onItemClickListener?.invoke(article)
            }
        }

        fun bind(article: Article) {
            with(itemView) {
                Glide.with(this)
                        .load(article.urlToImage)
                        .into(ivArticleImage)

                tvSource.text = article.source?.name
                tvDescription.text = article.description
                tvTitle.text = article.title
                tvPublishedAt.text = article.publishedAt
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.itemview_article, parent, false))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currentArticle = getItem(position)
        holder.bind(currentArticle)
    }

    fun getItemAt(position: Int): Article? {
        return getItem(position)
    }

    object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}