package com.dieunn.weatherapp.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dieunn.weatherapp.R
import com.dieunn.weatherapp.models.LocalCityData
import com.dieunn.weatherapp.viewmodels.WeatherDataViewModel
import java.lang.Exception

class LocalCityListAdapter(
    private var list: ArrayList<LocalCityData>,
    private val context: Context,
    private val viewModel: WeatherDataViewModel
) :
    RecyclerView.Adapter<LocalCityListAdapter.LocalCityListViewHolder>(), Filterable {


    inner class LocalCityListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var view = itemView
        var cityName: TextView
        var cityFullName: TextView

        init {
            cityName = itemView.findViewById(R.id.item_list_city_name)
            cityFullName = itemView.findViewById(R.id.item_list_city_fullname)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalCityListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_local_city, parent, false)


        view.setOnClickListener {
            parent.findNavController().navigate(R.id.search_fragment_to_main_fragment)
        }

        return LocalCityListViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocalCityListViewHolder, position: Int) {
        val city = list[position]
        holder.cityName.text = city.cityName
        holder.cityFullName.text = "${city.cityName}, ${city.adminName}, ${city.country}"
        holder.view.setOnClickListener {
            viewModel.currentCityName.value = city.cityName
            viewModel.currentCityLatitude.value = city.latitude
            viewModel.currentCityLongitude.value = city.longitude
            (context as Activity).onBackPressed()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraints: CharSequence?): FilterResults {
                val filteredList = ArrayList<LocalCityData>()
                if (constraints == null || constraints.isEmpty()) {
                    filteredList.addAll(list)
                } else {
                    val filterPattern = constraints.toString().lowercase().trim()
                    for (item in list) {
                        if (item.cityName.toString().lowercase().contains(filterPattern)) {
                            filteredList.add(item)
                        }
                    }
                }
                val result = FilterResults()
                result.values = filteredList
                return result
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraints: CharSequence?, result: FilterResults?) {
                try {
                    list.clear()
                    list.addAll(result?.values as ArrayList<LocalCityData>)
                } catch (e: Exception) {
                    list = ArrayList()
                }
                notifyDataSetChanged()
            }

        }
    }
}