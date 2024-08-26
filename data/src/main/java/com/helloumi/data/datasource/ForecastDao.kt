package com.helloumi.data.datasource

import androidx.lifecycle.LiveData
import androidx.room.*
import com.helloumi.data.entity.ForecastEntity

@Dao
interface ForecastDao {

    @Query("SELECT * FROM Forecast")
    fun getForecast(): LiveData<ForecastEntity>

    @Query("SELECT * FROM Forecast ORDER BY abs(lat-:lat) AND abs(lon-:lon) LIMIT 1")
    fun getForecastByCoord(lat: Double, lon: Double): LiveData<ForecastEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecast: ForecastEntity)

    @Transaction
    suspend fun deleteAndInsert(forecast: ForecastEntity) {
        deleteAll()
        insertForecast(forecast)
    }

    @Update
    suspend fun updateForecast(forecast: ForecastEntity)

    @Delete
    suspend fun deleteForecast(forecast: ForecastEntity)

    @Query("DELETE FROM Forecast")
    suspend fun deleteAll()

    @Query("Select count(*) from Forecast")
    suspend fun getCount(): Int
}
