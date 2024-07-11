package com.example.patrika.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.patrika.MainActivity
import com.example.patrika.R
import com.example.patrika.databinding.FragmentArticleBinding
import com.example.patrika.viewmodels.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var newsViewModel: NewsViewModel
    val args: ArticleFragmentArgs by navArgs()
    lateinit var _binding: FragmentArticleBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentArticleBinding.bind(view)

        newsViewModel = (activity as MainActivity).newsViewmodel
        val article = args.article
        _binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }
        _binding.fab.setOnClickListener {
            newsViewModel.addToSaved(article)
            Snackbar.make(view, "Article saved", Snackbar.LENGTH_SHORT).show()
        }
    }


}