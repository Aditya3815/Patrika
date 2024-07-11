package com.example.patrika

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.patrika.databinding.ActivityMainBinding
import com.example.patrika.db.ArticleDatabase
import com.example.patrika.repository.NewsRepository
import com.example.patrika.viewmodels.NewsViewModel
import com.example.patrika.viewmodels.NewsViewModelProviderFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var newsViewmodel:NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelFactory = NewsViewModelProviderFactory(application, newsRepository)
        newsViewmodel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]

    }
}