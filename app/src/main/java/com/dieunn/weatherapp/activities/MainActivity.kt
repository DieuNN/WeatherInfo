package com.dieunn.weatherapp.activities

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dieunn.weatherapp.databinding.ActivityMainBinding
import com.dieunn.weatherapp.viewmodels.WeatherDataViewModel


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val viewModel:WeatherDataViewModel by viewModels()

        // clear flag, set fullscreen
        val window: Window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        // change status bar color
        window.statusBarColor = Color.GRAY

        //navigation button color
        window.navigationBarColor = Color.TRANSPARENT


        setContentView(binding.root)

    }

}