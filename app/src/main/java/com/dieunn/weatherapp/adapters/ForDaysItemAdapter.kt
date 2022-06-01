package com.dieunn.weatherapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dieunn.weatherapp.R
import com.dieunn.weatherapp.models.Daily
import com.dieunn.weatherapp.parseLongTimestampToDate

class ForDaysItemAdapter(val context: Context, val list: List<Daily>) :
    RecyclerView.Adapter<ForDaysItemAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var forDaysItemDay: TextView
        var forDaysItemMinTemp: TextView
        var forDaysItemMaxTemp: TextView
        var forDaysItemIcon: ImageView
        var forDaysItemStatus: TextView

        init {
            forDaysItemDay = itemView.findViewById(R.id.for_day_list_item_day)
            forDaysItemMaxTemp = itemView.findViewById(R.id.for_day_list_item_max_temp)
            forDaysItemMinTemp = itemView.findViewById(R.id.for_day_list_item_min_temp)
            forDaysItemIcon = itemView.findViewById(R.id.for_day_list_item_icon)
            forDaysItemStatus = itemView.findViewById(R.id.for_day_list_item_status)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.for_days_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.forDaysItemDay.text =
            parseLongTimestampToDate(list[position].dt.toLong(), isDayOnly = true)
        holder.forDaysItemMinTemp.text = list[position].temp.min.toString() + "°"
        holder.forDaysItemMaxTemp.text = list[position].temp.max.toString() + "°"

        holder.forDaysItemStatus.text = list[position].weather[0].main

        holder.forDaysItemIcon.apply {
            when (list[position].weather[0].main) {
                "Thunderstorm" -> {
                    this.setImageResource(R.drawable.lightning)
                }
                "Drizzle" -> {
                    this.setImageResource(R.drawable.drizzle)
                }
                "Rain" -> {
                    this.setImageResource(R.drawable.rainy)
                }
                "Snow" -> {
                    this.setImageResource(R.drawable.snowflake)
                }
                "Clear" -> {
                    this.setImageResource(R.drawable.sun)
                }
                "Clouds" -> {
                    this.setImageResource(R.drawable.cloud)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}