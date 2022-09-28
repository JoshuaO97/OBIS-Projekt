package de.th.obis.geocachingapp.retrofit

import de.th.obis.geocachingapp.Route
import retrofit2.Response
import retrofit2.http.GET

interface RouteAPI {

    @GET("/routes")
    suspend fun getAllRoutes(): Response<List<Route>>
}
