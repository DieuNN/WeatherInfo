package com.dieunn.weatherapp.dao

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Query
import com.dieunn.weatherapp.models.LocalCityData

@Dao
interface LocalCityDao {
    @Query("select * from city")
    fun getAllCity():List <LocalCityData>

}