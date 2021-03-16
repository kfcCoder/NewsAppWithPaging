package com.example.newsappwithpaging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsappwithpaging.api.RetrofitInstance
import com.example.newsappwithpaging.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.Observer

/**
 * TODO:
 * 1.[ViewModel] switch map by countryCode
 * 2.[LoadStateAdapter] header& Footer
 * 3.[UI] addLoadStateListener
 */
const val TAG = "qq"
class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<NewsViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView.setupWithNavController(navHost.findNavController())

    }
}