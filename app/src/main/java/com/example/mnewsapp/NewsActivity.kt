package com.example.mnewsapp

//import com.example.mnewsapp.databinding.ActivityNewsBinding
//import com.example.mnewsapp.databinding.FragmentBreakingNewsBinding

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mnewsapp.databinding.ActivityNewsBinding
import com.example.mnewsapp.db.ArticleDatabase
import com.example.mnewsapp.repository.NewsRepository


class NewsActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityNewsBinding
    lateinit var viewModel: NewsViewModel
    private lateinit var navController: NavController

    private lateinit var navHostFragment: NavHostFragment



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(application,newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)


       val navHostFragment=supportFragmentManager.findFragmentById(com.example.mnewsapp.R.id.newsNavHostFragment) as NavHostFragment
        navController=navHostFragment.navController
//binding.bottomNavigationView.setupWithNavController(navController)
//        var navController = findNavController(R.id.newsNavHostFragment)

//       binding.bottomNavigationView?.setupWithNavController(newsNavHostFragment.findNavController())
        binding?.bottomNavigationView?.setupWithNavController(navController)
    }
}
