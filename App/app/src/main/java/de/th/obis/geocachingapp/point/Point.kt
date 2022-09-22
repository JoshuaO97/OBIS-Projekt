package de.th.obis.geocachingapp.point

import java.io.Serializable

data class Point (
    val latitude: Double,
    val longitude: Double,
    val code: Int
) : Serializable
