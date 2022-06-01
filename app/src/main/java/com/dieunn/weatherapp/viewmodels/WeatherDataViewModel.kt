package com.dieunn.weatherapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.dieunn.weatherapp.ApiInstance
import com.dieunn.weatherapp.GlobalVariables
import com.dieunn.weatherapp.models.ApiRetrieved
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WeatherDataViewModel(application: Application) : AndroidViewModel(application) {

    val currentCityName: MutableLiveData<String> = MutableLiveData("WeatherApp")

    val currentCityLatitude: MutableLiveData<Float> = MutableLiveData<Float>()

    val currentCityLongitude: MutableLiveData<Float> = MutableLiveData<Float>()

    val data: MutableLiveData<ApiRetrieved> = MutableLiveData<ApiRetrieved>()

    fun getData(lat:Float = 0f, lon: Float = 0f) = viewModelScope.launch {
        data.postValue(loadApiData(lat, lon))
    }

    private suspend fun loadApiData(
        lat: Float,
        lon: Float
    ): ApiRetrieved {

        return ApiInstance.api.getData(lat, lon)
    }
}