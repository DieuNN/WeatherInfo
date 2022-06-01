package com.dieunn.weatherapp.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dieunn.weatherapp.adapters.TodayRainAmountAdapter
import com.dieunn.weatherapp.adapters.TodayWindAdapter
import com.dieunn.weatherapp.databinding.FragmentTodayBinding
import com.dieunn.weatherapp.models.ApiRetrieved
import com.dieunn.weatherapp.models.Hourly
import com.dieunn.weatherapp.parseLongTimestampToDate
import com.dieunn.weatherapp.viewmodels.WeatherDataViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter


class TodayFragment : Fragment() {
    private lateinit var binding: FragmentTodayBinding
    private val viewModel: WeatherDataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodayBinding.inflate(layoutInflater)
        viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUi()
    }

    private fun updateUi() {
        viewModel.currentCityLongitude.observe(viewLifecycleOwner) {
            viewModel.currentCityLatitude.value?.let { it1 -> viewModel.getData(it1, it) }
        }
        viewModel.data.observe(viewLifecycleOwner) {
            binding.todayWeatherItem.todayWeatherItemCurrentTime.text =
                parseLongTimestampToDate(it.current.dt)
            binding.todayWeatherItem.todayWeatherItemCurrentTemperature.text =
                it.current.temp.toString()
            binding.todayWeatherItem.todayWeatherItemFeelLike.text =
                "Feel like " + it.current.feels_like.toString()
            binding.todayWeatherItem.todayWeatherItemStatus.text =
                it.current.weather[0].main.capitalize()

            binding.todayWeatherCurrent.todayWeatherCurrentHumidity.text =
                it.current.humidity.toString() + "%"
            binding.todayWeatherCurrent.todayWeatherCurrentPressure.text =
                it.current.pressure.toString() + "mBar"
            binding.todayWeatherCurrent.todayWeatherCurrentUvi.text = it.current.uvi.toString()
            drawLineChart(it)
            setAmountOfRain(it)
            setWindInfo(it.hourly)
        }
    }

    private fun drawLineChart(data: ApiRetrieved) {
        val lineList = ArrayList<Entry>()
        val hour = arrayListOf<Int>()
        val temp = arrayListOf<Double>()

        for (item in data.hourly) {
            hour.add(item.dt)
        }
        for (item in data.hourly) {
            temp.add(item.temp)
        }

        lineList.apply {
            for (i in 0 until hour.size step 2) {
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
        binding.todayWeatherChart.todayWeatherChartLineChart.apply {
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

    private fun setAmountOfRain(data: ApiRetrieved) {
        val adapter = TodayRainAmountAdapter(requireContext(), data.hourly)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.todayWeatherRain.todayWeatherRainRecyclerView.apply {
            this.layoutManager = layoutManager
            this.adapter = adapter
        }
    }

    private fun setWindInfo(data: List<Hourly>) {
        val adapter = TodayWindAdapter(requireContext(), data)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.todayWeatherWind.todayWeatherWindRecyclerView.apply {
            this.layoutManager = layoutManager
            this.adapter = adapter
        }
    }


}