package de.th.obis.geocachingapp

import de.th.obis.geocachingapp.point.Point
import java.io.Serializable

data class Route(
    val id: Int,
    val name: String,
    val length: Int,
    val ascent: Int,
    val descent: Int,
    val caches: MutableList<Point>
) : Serializable
