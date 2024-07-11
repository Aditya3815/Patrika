package com.example.patrika.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.example.patrika.R
import com.example.patrika.databinding.FragmentHostBinding

class HostFragment : Fragment(R.layout.fragment_host) {
    private lateinit var hostBinding: FragmentHostBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        hostBinding = FragmentHostBinding.inflate(inflater, container, false)
        return hostBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateToFragment(HeadlinesNewsFragment())
        hostBinding.bottomNav.setOnItemSelectedListener { item ->
            val anim = AnimationUtils.loadAnimation(context, R.anim.nav_item_anim)
            hostBinding.bottomNav.findViewById<View>(item.itemId)?.startAnimation(anim)

            when (item.itemId) {
                R.id.headlinesFragment -> {
                    navigateToFragment(HeadlinesNewsFragment())
                    true
                }
                R.id.searchFragment -> {
                    navigateToFragment(SearchFragment())
                    true
                }
                R.id.bookmarkFragment -> {
                    navigateToFragment(BookMarkFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun navigateToFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}
