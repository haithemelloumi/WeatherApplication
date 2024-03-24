package com.helloumi.todayweatherforecast.data.datasource

import androidx.room.*
import androidx.lifecycle.LiveData
import com.helloumi.todayweatherforecast.data.entity.CurrentWeatherEntity

@Dao
interface CurrentWeatherDao {

    @Query("SELECT * FROM CurrentWeather")
    fun getCurrentWeather(): LiveData<CurrentWeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(currentWeatherEntity: CurrentWeatherEntity)

    @Transaction
    suspend fun deleteAndInsert(currentWeatherEntity: CurrentWeatherEntity) {
        deleteCurrentWeather()
        insertCurrentWeather(currentWeatherEntity)
    }

    @Query("DELETE FROM CurrentWeather")
    suspend fun deleteCurrentWeather()

    @Query("Select count(*) from CurrentWeather")
    suspend fun getCount(): Int
}
