package com.example.patrika.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.patrika.MainActivity
import com.example.patrika.R
import com.example.patrika.adapter.RvAdapter
import com.example.patrika.databinding.FragmentBookMarkBinding
import com.example.patrika.viewmodels.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class BookMarkFragment : Fragment(R.layout.fragment_book_mark) {
    lateinit var newsViewModel: NewsViewModel
    lateinit var newsAdapter: RvAdapter
    lateinit var _biniding: FragmentBookMarkBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _biniding = FragmentBookMarkBinding.bind(view)
        newsViewModel = (activity as MainActivity).newsViewmodel
        setUpSavedRecycler()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_hostFragment_to_articleFragment,
                bundle
            )
        }
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT,
            ItemTouchHelper.UP or ItemTouchHelper.DOWN
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                newsViewModel.deleteArticle(article)
                Snackbar.make(view, "Article deleted", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo"){
                        newsViewModel.addToSaved(article)
                    }
                    show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(_biniding.recyclerFavourites)
        }
        newsViewModel.getSavedNews().observe(viewLifecycleOwner) { articles ->
            newsAdapter.differ.submitList(articles)
        }
    }

    fun setUpSavedRecycler() {
        newsAdapter = RvAdapter()
        _biniding.recyclerFavourites.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}