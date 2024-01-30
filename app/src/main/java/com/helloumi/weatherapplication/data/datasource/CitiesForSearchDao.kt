package com.helloumi.weatherapplication.data.datasource

import androidx.room.Dao
import androidx.room.Query
import com.helloumi.weatherapplication.data.entity.CityForSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesForSearchDao : BaseDao<CityForSearchEntity> {

    @Query("SELECT * FROM CityForSearch")
    fun getCities(): Flow<List<CityForSearchEntity>>

    @Query("SELECT * FROM CityForSearch WHERE fullName like '%' || :city || '%'|| '%' ORDER BY fullName DESC")
    fun getCityByName(city: String? = ""): Flow<List<CityForSearchEntity>>

    @Query("DELETE FROM CityForSearch")
    suspend fun deleteCities()

    @Query("Select count(*) from CityForSearch")
    suspend fun getCount(): Int
}
