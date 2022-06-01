package com.dieunn.weatherapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class LocalCityData(
    val cityName: String?,
    @PrimaryKey
    val id: Long?,
    val cityAscii: String?,
    val latitude: Float?,
    val longitude: Float?,
    val country: String?,
    val iso2: String?,
    val iso3: String?,
    val adminName: String?,
    val population: Long?,
    )
