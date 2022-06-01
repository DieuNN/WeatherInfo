package com.dieunn.weatherapp.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dieunn.weatherapp.R
import com.dieunn.weatherapp.adapters.TomorrowRainAmountAdapter
import com.dieunn.weatherapp.adapters.TomorrowWindAdapter
import com.dieunn.weatherapp.databinding.FragmentTomorrowBinding
import com.dieunn.weatherapp.models.ApiRetrieved
import com.dieunn.weatherapp.parseLongTimestampToDate
import com.dieunn.weatherapp.viewmodels.WeatherDataViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter


class TomorrowFragment : Fragment() {
    val viewModel: WeatherDataViewModel by activityViewModels()
    private lateinit var binding: FragmentTomorrowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTomorrowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUi()
    }

    private fun updateUi() {
        viewModel.data.observe(viewLifecycleOwner) {
            binding.tomorrowWeatherItem.tomorrowWeatherItemTime.text =
                parseLongTimestampToDate(it.daily[1].dt.toLong())
            binding.tomorrowWeatherItem.tomorrowWeatherInfoStatus.text = it.daily[1].weather[0].main
            binding.tomorrowWeatherItem.tomorrowWeatherInfoDayNight.text =
                "Day ${it.daily[1].temp.day.toInt()}°, night ${it.daily[1].temp.night.toInt()}°"
            setupChart(it)
            binding.tomorrowWeatherDetail.tomorrowWeatherDetailHumidity.text =
                it.daily[1].humidity.toString() + "%"
            binding.tomorrowWeatherDetail.tomorrowWeatherDetailAmountOfRain.text =
                if (it.daily[1].rain == null) "0mm" else it.daily[1].rain.toString() + "mm"
            setAmountOfRainView(it)
            setWindSpeed(it)
        }
    }

    private fun setWindSpeed(data: ApiRetrieved) {
        binding.tomorrowWeatherWind.tomorrowWeatherWindRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = TomorrowWindAdapter(requireContext(), data.daily)
        }
    }

    private fun setAmountOfRainView(data: ApiRetrieved) {
        binding.tomorrowWeatherRain.tomorrowWeatherRainRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = TomorrowRainAmountAdapter(requireContext(), data.daily)
        }
    }

    private fun setupChart(data: ApiRetrieved) {
        val lineList = ArrayList<Entry>()
        val hour = arrayListOf<Int>()
        val temp = arrayListOf<Double>()

        for (item in data.daily) {
            hour.add(item.dt)
        }
        for (item in data.daily) {
            temp.add(item.temp.min)
        }

        lineList.apply {
            for (i in 0 until hour.size) {
                this.add(Entry(hour[i].toFloat(), temp[i].toFloat()))
            }
        }
        val lineDataSet = LineDataSet(lineList, "")
        lineDataSet.apply {
            this.valueTextColor = Color.WHITE
            this.setDrawFilled(true)
            this.color = Color.WHITE

        }
        val lineData = LineData(lineDataSet)
        binding.tomorrowWeatherChart.tomorrowWeatherChart.apply {
            this.data = lineData
            this.description.text = "Weather"
            this.description.textColor = Color.WHITE
            this.xAxis.textColor = Color.WHITE
            this.axisLeft.textColor = Color.WHITE
            this.axisRight.setDrawLabels(false)
            this.xAxis.position = XAxis.XAxisPosition.BOTTOM
            this.xAxis.valueFormatter = object : IndexAxisValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return parseLongTimestampToDate(value.toLong(), isTimeOnly = true)
                }
            }
            this.invalidate()
        }
    }


}