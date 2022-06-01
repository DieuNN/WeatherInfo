package com.dieunn.weatherapp.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dieunn.weatherapp.dao.LocalCityDao
import com.dieunn.weatherapp.models.LocalCityData

@Database(entities = [LocalCityData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun localCityDao(): LocalCityDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "city_database"
                ).allowMainThreadQueries()
                    .createFromAsset("prepopulate/city_database")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}