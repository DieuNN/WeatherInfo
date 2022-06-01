package com.dieunn.weatherapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dieunn.weatherapp.R
import com.dieunn.weatherapp.adapters.ForDaysItemAdapter
import com.dieunn.weatherapp.databinding.FragmentForDaysBinding
import com.dieunn.weatherapp.models.ApiRetrieved
import com.dieunn.weatherapp.viewmodels.WeatherDataViewModel


class ForDaysFragment : Fragment() {
    private lateinit var binding: FragmentForDaysBinding
    private val viewModel: WeatherDataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentForDaysBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUi()
    }

    private fun updateUi() {
        viewModel.data.observe(viewLifecycleOwner) {
            setUpRecyclerView(data = it)
        }
    }

    private fun setUpRecyclerView(data: ApiRetrieved) {
        binding.forDaysRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ForDaysItemAdapter(requireContext(), data.daily)
        }
    }


}