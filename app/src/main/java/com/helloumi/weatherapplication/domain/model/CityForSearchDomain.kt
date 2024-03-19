package com.helloumi.weatherapplication.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CityForSearchDomain(
    val id: String,
    val name: String
): Parcelable
