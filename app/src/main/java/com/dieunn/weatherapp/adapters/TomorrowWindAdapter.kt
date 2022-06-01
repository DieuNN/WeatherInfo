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
import com.dieunn.weatherapp.models.Hourly
import com.dieunn.weatherapp.parseLongTimestampToDate
import java.lang.Exception

class TomorrowWindAdapter(private val context: Context, private val dataList: List<Daily>) :
    RecyclerView.Adapter<TomorrowWindAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var todayWindItemIcon: ImageView
        var todayWindItemSpeed: TextView
        var todayWindItemTime: TextView

        init {
            todayWindItemIcon =
                itemView.findViewById<ImageView>(R.id.tomorrow_weather_wind_item_icon)
            todayWindItemSpeed =
                itemView.findViewById<TextView>(R.id.tomorrow_weather_wind_item_wind_speed)
            todayWindItemTime =
                itemView.findViewById<TextView>(R.id.tomorrow_weather_wind_item_time)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.tomorrow_weather_wind_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.todayWindItemIcon.rotation = dataList[position].wind_deg.toFloat()
        holder.todayWindItemSpeed.text = dataList[position].wind_speed.toString()
        holder.todayWindItemTime.text =
            parseLongTimestampToDate(dataList[position].dt.toLong(), isDayOnly = true)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}