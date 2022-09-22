package de.th.obis.geocachingapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import de.th.obis.geocachingapp.databinding.ActivityOverviewBinding
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import kotlin.math.pow
import kotlin.math.roundToInt

class OverviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOverviewBinding
    private lateinit var map: MapView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOverviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val route = intent.getSerializableExtra("EXTRA_ROUTE") as Route
        binding.tvName.text = route.name
        binding.tvLength.text = "Länge: ${round(route.length.toDouble() / 1000, 1)} km" // parse meters into km with 1 decimal point
        binding.tvAscent.text = "Anstieg: ${route.ascent} m"
        binding.tvDescent.text = "Abstieg: ${route.descent} m"
        binding.tvNumCaches.text = "Anzahl Caches: ${route.caches.size}"

        createMap(route)
    }


    private fun round(value: Double, decimalPoints: Int): Double {
        val d = 10.0.pow(decimalPoints.toDouble())
        return ((value * d).roundToInt()) / d
    }

    private fun createMap(route: Route) {
        map = binding.mvMap
        map.setTileSource(TileSourceFactory.MAPNIK)
        Configuration.getInstance().load(applicationContext, getSharedPreferences("prefs", MODE_PRIVATE))

        val points = mutableListOf<GeoPoint>() // put the locations of all caches in a list
        for (i in route.caches.indices) {
            val latitude = route.caches[i].latitude
            val longitude = route.caches[i].longitude

            val gp = GeoPoint(latitude, longitude)
            points.add(gp)
        }

        val controller = map.controller // show the correct section of the map
        controller.setCenter(points[points.size / 2])
        controller.setZoom(14.5)

        for (i in points.indices) { // add markers where the caches are
            val marker = Marker(map)
            marker.position = points[i]
            marker.title = "Cache ${i + 1}"
            map.overlays.add(marker)
            map.invalidate()
        }
    }
}
