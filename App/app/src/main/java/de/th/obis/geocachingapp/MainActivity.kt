package de.th.obis.geocachingapp

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import de.th.obis.geocachingapp.databinding.ActivityMainBinding
import de.th.obis.geocachingapp.retrofit.RetrofitInstance
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var routeAdapter: RouteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intiRecyclerView()

        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getAllRoutes()
            }
            catch (e: IOException) {
                e.message?.let { Log.e("MainActivity.kt", it) }
                return@launchWhenCreated
            }
            catch (e: HttpException) {
                e.message?.let { Log.e("MainActivity.kt", it) }
                return@launchWhenCreated
            }

            if (response.isSuccessful && response.body() != null) {
                routeAdapter.routes = response.body()!!

                requestPermissions()
            }
            else {
                Log.e("MainActivity.kt", "Response failed")
            }

        }

    }


    private fun intiRecyclerView() {
        binding.rvRoutes.apply {
            routeAdapter = RouteAdapter(this@MainActivity)
            adapter = routeAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun requestPermissions() {
        val permissionsToRequest = mutableListOf<String>()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), 0)
        }
    }
}
