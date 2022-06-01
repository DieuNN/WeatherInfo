package com.dieunn.weatherapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dieunn.weatherapp.R
import com.dieunn.weatherapp.adapters.TodayRainAmountAdapter.*
import com.dieunn.weatherapp.models.Hourly
import com.dieunn.weatherapp.parseLongTimestampToDate
import java.lang.Exception

class TodayRainAmountAdapter(private val context: Context, private val dataList: List<Hourly>) :
    RecyclerView.Adapter<ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var todayWeatherRainItemIcon: ImageView
        var todayWeatherRainItemVolume: TextView
        var todayWeatherRainItemTime: TextView

        init {
            todayWeatherRainItemIcon =
                itemView.findViewById<ImageView>(R.id.today_weather_rain_item_icon)
            todayWeatherRainItemVolume =
                itemView.findViewById<TextView>(R.id.today_weather_rain_item_volume)
            todayWeatherRainItemTime =
                itemView.findViewById<TextView>(R.id.today_weather_rain_item_time)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.today_weather_rain_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (dataList[position].rain != null) {
            holder.todayWeatherRainItemIcon.setImageResource(R.drawable.rainy)
        } else {
            holder.todayWeatherRainItemIcon.setImageResource(R.drawable.cloud)
        }

        try {
            if (dataList[position].rain?.`1h` == null) {
                holder.todayWeatherRainItemVolume.text = "0.0"
            } else {
                holder.todayWeatherRainItemVolume.text = dataList[position].rain?.`1h`.toString()
            }
        } catch (e:Exception) {
            holder.todayWeatherRainItemVolume.text = "No data"
        }

        holder.todayWeatherRainItemTime.text =
            parseLongTimestampToDate(dataList[position].dt.toLong(), isTimeOnly = true)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}