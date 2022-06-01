package com.dieunn.weatherapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.dieunn.weatherapp.R
import com.dieunn.weatherapp.adapters.ViewPagerAdapter
import com.dieunn.weatherapp.databinding.FragmentMainBinding
import com.dieunn.weatherapp.viewmodels.WeatherDataViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val viewModel: WeatherDataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        setUpViewPager()
        updateUi()
        onButtonsClick(view)


    }

    private fun onButtonsClick(view: View) {
        binding.btnOpenMenu.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.main_fragment_to_menu_fragment)
        }
        binding.btnOpenSearch.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.main_to_search_fragment)
        }
    }

    private fun updateUi() {
        viewModel.currentCityLongitude.observe(viewLifecycleOwner) {
            viewModel.getData(
                viewModel.currentCityLatitude.value!!,
                viewModel.currentCityLongitude.value!!
            )
        }
        viewModel.currentCityName.observe(viewLifecycleOwner) {
            binding.toolbarTitle.text = it
        }

    }

    private fun setUpViewPager() {
        binding.viewPager.adapter =
            ViewPagerAdapter(requireActivity().supportFragmentManager, requireActivity().lifecycle)
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { binding.viewPager.currentItem = it }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })
    }


}