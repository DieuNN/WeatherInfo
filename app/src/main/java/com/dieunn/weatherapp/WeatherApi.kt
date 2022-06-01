package com.dieunn.weatherapp

import com.dieunn.weatherapp.models.ApiRetrieved
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("onecall?appid=${API_KEY}")
    suspend fun getData(
        @Query("lat") lat:Float,
        @Query("lon") lon:Float,
        @Query("units") unit:String = "metric"
    ):ApiRetrieved
}