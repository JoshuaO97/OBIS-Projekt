package de.th.obis.geocachingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import de.th.obis.geocachingapp.databinding.ActivityMainBinding
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
            }
            else {
                Log.e("MainActivity.kt", "Response failed")
            }

        }

    }

    fun intiRecyclerView() {
        binding.rvRoutes.apply {
            routeAdapter = RouteAdapter()
            adapter = routeAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}
