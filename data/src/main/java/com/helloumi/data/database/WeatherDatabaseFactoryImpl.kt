package com.helloumi.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import com.helloumi.data.database.WeatherDatabase.Companion.DATABASE_NAME
import javax.inject.Inject

class WeatherDatabaseFactoryImpl @Inject constructor() : WeatherDatabaseFactory {
    override fun build(context: Context, bddFile: String, vararg migrations: Migration) =
        Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build() //The reason we can construct a database from the repo
}
