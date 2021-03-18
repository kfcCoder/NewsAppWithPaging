package com.example.newsappwithpaging.ui

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappwithpaging.R
import com.example.newsappwithpaging.paging.NewsLoadStateAdapter
import com.example.newsappwithpaging.paging.NewsPagingAdapter
import com.example.newsappwithpaging.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * TODO:
 * 1.close kb
 */
class SearchNewsFragment : Fragment(R.layout.fragment_search_news){

    private val sharedViewModel by activityViewModels<NewsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagingAadpter = NewsPagingAdapter()

        rvSearchNews.apply {
            adapter = pagingAadpter.withLoadStateHeaderAndFooter(
                    header = NewsLoadStateAdapter { pagingAadpter.retry() },
                    footer = NewsLoadStateAdapter { pagingAadpter.retry() },
            )
            layoutManager = LinearLayoutManager(requireContext())
        }

        // 監控EditText輸入變化
        etSearch.addTextChangedListener {
            lifecycleScope.launch {
                delay(500L)
                it.let {
                    if (it.toString().isNotEmpty()) {
                        sharedViewModel.setSearchNewsQuery(it.toString())
                    }

                }
            }

        }

        // 按下Enter後關閉鍵盤
        etSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if ((event?.getAction() == KeyEvent.ACTION_DOWN ) &&
                        (event.keyCode == KeyEvent.KEYCODE_ENTER) ) {
                    closeKeyboard()
                    return true
                }
                return false
            }
        })

        // observing
        sharedViewModel.searchedNewsLive.observe(viewLifecycleOwner, Observer {
            pagingAadpter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        // 監控LoadState
        pagingAadpter.addLoadStateListener {
            pbPaginationSearchNews.isVisible = it.source.refresh is LoadState.Loading
        }

        // handle click to ArticleFragment
        pagingAadpter.setOnItemClickListener {
            Bundle().apply {
                putSerializable("article", it)
                //putParcelable("article", it)
                findNavController().navigate(
                        R.id.action_searchNewsFragment_to_articleFragment,this)
            }
        }
    }

    // close keyboard
    fun closeKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}