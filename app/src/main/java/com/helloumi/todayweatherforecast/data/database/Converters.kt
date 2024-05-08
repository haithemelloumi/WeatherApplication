package com.helloumi.todayweatherforecast.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.helloumi.todayweatherforecast.domain.model.ListItem
import com.helloumi.todayweatherforecast.domain.model.WeatherItem
import com.helloumi.todayweatherforecast.domain.model.WeatherItemList
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

object Converters {

    @TypeConverter
    @JvmStatic
    fun stringToList(data: String?): List<ListItem>? {
        if (data == null) {
            return emptyList()
        }

        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, ListItem::class.java)
        val adapter = moshi.adapter<List<ListItem>>(type)
        return adapter.fromJson(data)
    }

    @TypeConverter
    @JvmStatic
    fun listToString(objects: List<ListItem>): String {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, ListItem::class.java)
        val adapter = moshi.adapter<List<ListItem>>(type)
        return adapter.toJson(objects)
    }

    @TypeConverter
    @JvmStatic
    fun weatherStringToList(data: String?): WeatherItemList? {
        if (data == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<WeatherItemList>() {}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    @JvmStatic
    fun weatherListToString(objects: WeatherItemList): String {
        val gson = Gson()
        val type = object : TypeToken<List<WeatherItem>>() {}.type
        return gson.toJson(objects, type)
    }
}
