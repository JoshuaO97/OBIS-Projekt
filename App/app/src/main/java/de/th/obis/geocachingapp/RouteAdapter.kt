package de.th.obis.geocachingapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import de.th.obis.geocachingapp.databinding.ItemRouteBinding
import kotlin.math.pow
import kotlin.math.roundToInt

class RouteAdapter(private var con: Context) : RecyclerView.Adapter<RouteAdapter.RouteViewHolder>() {

    inner class RouteViewHolder(val binding: ItemRouteBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Route>() {
        override fun areItemsTheSame(oldItem: Route, newItem: Route): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Route, newItem: Route): Boolean {
            return oldItem == newItem
        }
    }

    private val listDiffer = AsyncListDiffer(this, diffCallback)
    var routes: List<Route>
        get() = listDiffer.currentList
        set(value) { listDiffer.submitList(value) }


    override fun getItemCount(): Int { // num of items in RecyclerView
        return routes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteViewHolder { // is called, when a new item becomes visible
        return RouteViewHolder(ItemRouteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) { // set data to RecyclerView
        holder.binding.apply {
            val route = routes[position]

            tvName.text = route.name

            var length = route.length.toDouble()
            length = round(length / 1000, 1) // parse meters into km with 1 decimal point
            tvLength.text = "Länge: $length km"

            val numCaches = route.caches.size
            tvNumCaches.text = "Anzahl Caches: $numCaches"

            val sentRoute = Route(route.id, route.name, route.length, route.ascent, route.descent, route.caches)
            root.setOnClickListener {
                val routeIntent = Intent(con, OverviewActivity::class.java)
                routeIntent.putExtra("EXTRA_ROUTE", sentRoute)
                con.startActivity(routeIntent)
            }
        }
    }


    private fun round(value: Double, decimalPoints: Int): Double {
        val d = 10.0.pow(decimalPoints.toDouble())
        return ((value * d).roundToInt()) / d
    }
}
