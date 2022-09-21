package de.th.obis.geocachingapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: RouteAPI by lazy {
        Retrofit.Builder()
            .baseUrl("http://169.254.13.104:6300") // 169.254.13.104
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RouteAPI::class.java)
    }
}
