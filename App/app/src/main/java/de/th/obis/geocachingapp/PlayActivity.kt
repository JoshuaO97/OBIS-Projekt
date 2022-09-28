package de.th.obis.geocachingapp

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.*
import de.th.obis.geocachingapp.databinding.ActivityPlayBinding
import de.th.obis.geocachingapp.point.Point
import kotlin.math.roundToInt

class PlayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var curCache: Point
    private var numCaches = 0
    private var curIndex = 0 // index of the current cache in list routes.caches


    @SuppressLint("MissingPermission", "VisibleForTests") // permissions are already requested in MainActivity.kt
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val route = intent.getSerializableExtra("EXTRA_ROUTE_2") as Route
        curCache = route.caches[curIndex]
        numCaches = route.caches.size

        binding.tvName.text = route.name
        binding.etCode.isEnabled = false
        binding.btnNext.isEnabled = false

        binding.btnNext.setOnClickListener {
            if (binding.etCode.text.isEmpty()) {
                Toast.makeText(this, "Gib bitte einen Code ein", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val enteredCode: Int = binding.etCode.text.toString().toInt()
            if (enteredCode != curCache.code) {
                Toast.makeText(this, "Code ist nicht korrekt!", Toast.LENGTH_SHORT).show()
                binding.etCode.text.clear()
                return@setOnClickListener
            }
            else {
                Toast.makeText(this, "Code ist korrekt", Toast.LENGTH_SHORT).show()
                binding.etCode.text.clear()
            }

            curIndex++
            if (curIndex < numCaches) {
                curCache = route.caches[curIndex]
            }
            else {
                Toast.makeText(this, "Glückwunsch! Du hast alle Caches gefunden", Toast.LENGTH_LONG).show()
                finish()
            }
        }

        fusedLocationProviderClient = FusedLocationProviderClient(this)

        val locationRequest = LocationRequest.create().apply {
            interval = 4000
            fastestInterval = 2000
            priority = Priority.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private val locationCallback = object : LocationCallback() { // is triggered in every interval
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            locationResult.locations.let { locations ->
                for(location in locations) {
                     updateUIValues(location)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUIValues(curLocation: Location) {
        val destLocation = Location("")
        destLocation.latitude = curCache.latitude
        destLocation.longitude = curCache.longitude

        val bearing = curLocation.bearingTo(destLocation)
        val distance = curLocation.distanceTo(destLocation)

        if (distance <= 15) { // geofence with a radius of 15 m around the cache
            binding.etCode.isEnabled = true
            binding.btnNext.isEnabled = true
        }
        else {
            binding.etCode.isEnabled = false
            binding.btnNext.isEnabled = false
        }

        binding.tvDistance.text = "Cache ${curIndex + 1}/$numCaches in ${distance.roundToInt()} m"

        if (distance > 1) { // change direction of arrow
            if (bearing <= 22) {
                binding.imgArrow.setImageResource(R.drawable.arrow_north)
            }
            else if (22 < bearing && bearing <= 68) {
                binding.imgArrow.setImageResource(R.drawable.arrow_north_east)
            }
            else if (68 < bearing && bearing <= 135) {
                binding.imgArrow.setImageResource(R.drawable.arrow_east)
            }
            else if (138 < bearing && bearing <= 225) {
                binding.imgArrow.setImageResource(R.drawable.arrow_south)
            }
            else if (225 < bearing && bearing <= 295) {
                binding.imgArrow.setImageResource(R.drawable.arrow_west)
            }
            else if (295 < bearing && bearing <= 338) {
                binding.imgArrow.setImageResource(R.drawable.arrow_north_west)
            }
            else if (338 < bearing) {
                binding.imgArrow.setImageResource(R.drawable.arrow_north)
            }
        }
        else {
            binding.tvDistance.text = "Cache ${curIndex + 1}/$numCaches in < 1 m"
            binding.imgArrow.setImageResource(R.drawable.arrow_north)
        }
    }
}
