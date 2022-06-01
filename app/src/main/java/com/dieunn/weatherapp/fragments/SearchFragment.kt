package com.dieunn.weatherapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dieunn.weatherapp.adapters.LocalCityListAdapter
import com.dieunn.weatherapp.database.AppDatabase
import com.dieunn.weatherapp.databinding.FragmentSearchBinding
import com.dieunn.weatherapp.viewmodels.WeatherDataViewModel

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: WeatherDataViewModel by activityViewModels()


        val cities = AppDatabase.getDatabase(requireContext()).localCityDao().getAllCity()
        val arrayAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            cities.map { it.cityName })
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        val linerLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewListCity.layoutManager = linerLayoutManager
        val cityListAdapter = LocalCityListAdapter(ArrayList(cities), requireContext(), viewModel)
        binding.recyclerViewListCity.adapter =
            cityListAdapter

        binding.autoCompleteTextView.addTextChangedListener {
            cityListAdapter.filter.filter(it.toString())
        }

        binding.btnSearchBackToMainFragment.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}