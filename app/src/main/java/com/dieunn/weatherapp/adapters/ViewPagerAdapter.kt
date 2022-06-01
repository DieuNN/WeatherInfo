package com.dieunn.weatherapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dieunn.weatherapp.fragments.ForDaysFragment
import com.dieunn.weatherapp.fragments.TodayFragment
import com.dieunn.weatherapp.fragments.TomorrowFragment

class ViewPagerAdapter(
    private val fragmentManager: FragmentManager,
    private val lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {



    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TodayFragment()
            1 -> TomorrowFragment()
            2 -> ForDaysFragment()
            else -> TodayFragment()
        }
    }


}