package com.example.newsappwithpaging.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsappwithpaging.R
import com.example.newsappwithpaging.model.Article
import kotlinx.android.synthetic.main.itemview_article.view.*

class NewsPagingAdapter
    : PagingDataAdapter<Article, NewsPagingAdapter.ArticlePagingViewHolder>(DiffCallback) {

    /** handle click */
    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

    inner class ArticlePagingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val article = getItem(absoluteAdapterPosition)
                if (article != null) {
                    onItemClickListener?.invoke(article)
                }
            }
        }

        fun bind(article: Article) {
            with(itemView) {
                Glide.with(this)
                        .load(article.urlToImage)
                        .into(ivArticleImage)

                tvPublishedAt.text = article.publishedAt
                tvTitle.text = article.title
                tvDescription.text = article.description
                tvSource.text = article.source?.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlePagingViewHolder {
        return ArticlePagingViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.itemview_article, parent, false))
    }





    override fun onBindViewHolder(holder: ArticlePagingViewHolder, position: Int) {
        val currentArticle = getItem(position)
        if (currentArticle != null) {
            holder.bind(currentArticle)
        }
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