package com.example.mnewsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mnewsapp.NewsActivity
import com.example.mnewsapp.NewsViewModel
import com.example.mnewsapp.R
import com.example.mnewsapp.adapters.NewsAdapter
import com.example.mnewsapp.databinding.FragmentBreakingNewsBinding
//import com.example.mnewsapp.databinding.ActivityNewsBinding
//import com.example.mnewsapp.databinding.FragmentBreakingNewsBinding
//import com.example.mnewsapp.ui.base.BaseFragment
import com.example.mnewsapp.util.Resource
//import kotlinx.android.synthetic.main.fragment_breaking_news.*
//import kotlinx.android.synthetic.main.fragment_search_news.*
//import kotlinx.android.synthetic.main.fragment_search_news.paginationProgressBar

class BreakingNewsFragment : Fragment() {

    private var _binding: FragmentBreakingNewsBinding?=null
    private val binding get()=_binding!!



    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    val TAG = "BreakingNewsFragment"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)

             return binding?.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()



       viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
      binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
       binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}