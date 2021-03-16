package com.example.newsappwithpaging.ui

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.newsappwithpaging.MainActivity
import com.example.newsappwithpaging.R
import com.example.newsappwithpaging.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_article.*


class ArticleFragment : Fragment(R.layout.fragment_article) {

    private val sharedViewModel by activityViewModels<NewsViewModel>()

    private val args by navArgs<ArticleFragmentArgs>() // <夾帶args的Fragment>



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 由navArgs取出夾帶數據
        val article = args.article

        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        fab.setOnClickListener {
            sharedViewModel.upsert(article) // db operation
            Snackbar.make(it, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
        }
    }
}