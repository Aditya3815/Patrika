package com.example.patrika.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.patrika.MainActivity
import com.example.patrika.R
import com.example.patrika.adapter.RvAdapter
import com.example.patrika.databinding.FragmentHeadlinesNewsBinding
import com.example.patrika.utils.Constants
import com.example.patrika.utils.Resource
import com.example.patrika.viewmodels.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class HeadlinesNewsFragment : Fragment(R.layout.fragment_headlines_news) {
    lateinit var newsViewModel: NewsViewModel
    lateinit var newsAdapter: RvAdapter
    lateinit var retryButton: Button
    lateinit var errorText: TextView
    lateinit var itemHeadLinesError: CardView
    lateinit var _binding: FragmentHeadlinesNewsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHeadlinesNewsBinding.bind(view)


        itemHeadLinesError = view.findViewById(R.id.itemHeadlinesError)
        val inflater =
            requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val errorView: View = inflater.inflate(R.layout.item_error, null)
        retryButton = errorView.findViewById(R.id.retryButton)
        errorText = errorView.findViewById(R.id.errorText)
        newsViewModel = (activity as MainActivity).newsViewmodel
        setupHeadlinesRecyclerView()

        retryButton.setOnClickListener {
            newsViewModel.getHeadlines("in")
        }


        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_hostFragment_to_articleFragment,
                bundle
            )
        }
        newsViewModel.headlines.observe(viewLifecycleOwner, Observer {
            response ->
            when(response){
                is Resource.Success<*> -> {
                    hideProgressBar()
                    hideErrorMessage()
                    response.data?.let {
                        newsResponse->{
                            newsAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages = newsResponse.totalResults/Constants.QUERY_PAGE_SIZE + 2
                        isLastPage = newsViewModel.headlinesPage == totalPages
                        if(isLastPage){
                            _binding.recyclerHeadlines.setPadding(0,0,0,0)
                        }
                    }
                    }
                }

                is Resource.Error<*> -> {
                    hideProgressBar()
                    response.message?.let {
                        message ->
                        Snackbar.make(view, "Error", Snackbar.LENGTH_SHORT).show()
                        showErrorMessage(message)
                    }
                }
                is Resource.Loading<*> -> showProgressBar()
            }
        })


    }

    var isError = false
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private fun hideProgressBar() {
        _binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private fun showProgressBar() {
        _binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun hideErrorMessage() {
        itemHeadLinesError.visibility = View.INVISIBLE
        isError = false
    }

    private fun showErrorMessage(message: String) {
        itemHeadLinesError.visibility = View.VISIBLE
        errorText.text = message
        isError = true
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNoErrors = !isError
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate =
                isNoErrors && isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                newsViewModel.getHeadlines("in")
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

    }

    private fun setupHeadlinesRecyclerView() {
        newsAdapter = RvAdapter()
        _binding.recyclerHeadlines.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@HeadlinesNewsFragment.scrollListener)
        }
    }
}