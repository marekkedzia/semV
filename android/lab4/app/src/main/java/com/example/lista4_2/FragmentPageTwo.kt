package com.example.lista4_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FragmentPageTwo : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabs: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_page_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = view.findViewById(R.id.view_pager)
        tabs = view.findViewById(R.id.tabs)

        viewPager.adapter = MyPagerAdapter(this)

        val tabText = arrayOf("Theme 1", "Theme 2", "Theme 3")

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = tabText[position]
        }.attach()
    }

    private inner class MyPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 3
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> FragmentQuote1()
                1 -> FragmentQuote2()
                2 -> FragmentQuote3()
                else -> throw IllegalStateException("Invalid position $position")
            }
        }
    }
}
