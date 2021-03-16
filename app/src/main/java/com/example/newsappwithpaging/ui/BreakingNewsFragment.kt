package com.example.newsappwithpaging.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappwithpaging.R
import com.example.newsappwithpaging.paging.NewsLoadStateAdapter
import com.example.newsappwithpaging.paging.NewsPagingAdapter
import com.example.newsappwithpaging.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_breaking_news.*

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    private val sharedViewModel by activityViewModels<NewsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagingAadpter = NewsPagingAdapter()

        // setup RecyclerView
        rvBreakingNews.apply {
            adapter = pagingAadpter.withLoadStateHeaderAndFooter(
                    header = NewsLoadStateAdapter { pagingAadpter.retry() },
                    footer = NewsLoadStateAdapter { pagingAadpter.retry() },
            )
            layoutManager = LinearLayoutManager(requireContext())
        }

        // Paging
        sharedViewModel.breakingNewsLive.observe(viewLifecycleOwner, Observer {
            pagingAadpter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        pagingAadpter.addLoadStateListener {
            pbPaginationBreakingNews.isVisible = it.source.refresh is LoadState.Loading
        }

        // handle click to ArticleFragment
        pagingAadpter.setOnItemClickListener {
            Bundle().apply {
                putSerializable("article", it)
                findNavController().navigate(
                        R.id.action_breakingNewsFragment_to_articleFragment,this)
            }
        }


   }



}