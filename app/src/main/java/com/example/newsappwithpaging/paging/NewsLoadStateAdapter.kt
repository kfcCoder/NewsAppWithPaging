package com.example.newsappwithpaging.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappwithpaging.R
import kotlinx.android.synthetic.main.load_state_footer.view.*

class NewsLoadStateAdapter(
        private val retry: () -> Unit
)
    : LoadStateAdapter<NewsLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.load_state_footer, parent, false) )
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.btRetryFooter.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            with(itemView) {
                pbFooter.isVisible = loadState is LoadState.Loading
                btRetryFooter.isVisible = loadState !is LoadState.Loading
                tvErrorFooter.isVisible = loadState !is LoadState.Loading
            }
        }
    }
}