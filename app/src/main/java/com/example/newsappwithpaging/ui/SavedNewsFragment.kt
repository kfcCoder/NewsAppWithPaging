package com.example.newsappwithpaging.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappwithpaging.R
import com.example.newsappwithpaging.adapter.NewsAdapter
import com.example.newsappwithpaging.paging.NewsPagingAdapter
import com.example.newsappwithpaging.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_saved_news.*

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    private val sharedViewModel by activityViewModels<NewsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsAadpter = NewsAdapter()

        // setup RecyclerView
        rvSavedNews.apply {
            adapter = newsAadpter
            layoutManager = LinearLayoutManager(requireContext())
        }

        // observing
        sharedViewModel.getSavedNewsLive().observe(viewLifecycleOwner, Observer {
            newsAadpter.submitList(it)
        })

        // handle click to ArticleFragment
        newsAadpter.setOnItemClickListener {
            Bundle().apply {
                putSerializable("article", it)
                findNavController().navigate(
                        R.id.action_savedNewsFragment_to_articleFragment, this)
            }
        }

        // swipe to remove
        ItemTouchHelper(
                object : ItemTouchHelper.SimpleCallback(
                        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                ) {
                    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val position = viewHolder.absoluteAdapterPosition
                        val article = newsAadpter.getItemAt(position)!!
                        sharedViewModel.delete(article)
                        Snackbar.make(view, "Successfully deleted article", Snackbar.LENGTH_LONG).apply {
                            setAction("Undo") {
                                sharedViewModel.upsert(article)
                            }
                            show()
                        }
                    }
                }).attachToRecyclerView(rvSavedNews)


    }
}