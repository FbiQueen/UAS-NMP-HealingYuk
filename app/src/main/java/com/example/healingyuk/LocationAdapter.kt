package com.example.healingyuk

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.healingyuk.databinding.ItemHealingPlaceBinding
import com.squareup.picasso.Picasso

class LocationAdapter(
    private val context: Context,
    private val locationList: MutableList<Location>,
    private val isFromFavourite: Boolean = false
) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    inner class LocationViewHolder(val binding: ItemHealingPlaceBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding = ItemHealingPlaceBinding.inflate(LayoutInflater.from(context), parent, false)
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = locationList[position]
        with(holder.binding) {
            tvPlaceName.text = location.name
            tvPlaceCategory.text = location.category
            tvShortDescription.text = location.shortDescription
            Picasso.get().load(location.imageUrl).into(ivPlaceImage)

            btnReadMore.text = if (isFromFavourite) "Remove Favourite" else "Read More"

            btnReadMore.setOnClickListener {
                val user = SessionManager.getUser(context)
                if (user == null) {
                    Toast.makeText(context, "User belum login", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (isFromFavourite) {
                    // Remove Favorite
                    val request = object : StringRequest(Request.Method.POST, "http://10.0.2.2/healing/remove_favorite.php",
                        {
                            Toast.makeText(context, "Dihapus dari favorit", Toast.LENGTH_SHORT).show()
                            locationList.removeAt(position)
                            notifyItemRemoved(position)
                            notifyItemRangeChanged(position, locationList.size)
                        },
                        {
                            Toast.makeText(context, "Gagal menghapus", Toast.LENGTH_SHORT).show()
                        }) {
                        override fun getParams(): MutableMap<String, String> {
                            return mutableMapOf(
                                "user_id" to user.id.toString(),
                                "location_id" to location.id.toString()
                            )
                        }
                    }
                    Volley.newRequestQueue(context).add(request)

                } else {
                    // Navigate to Details
                    val intent = Intent(context, HealingDetailsActivity::class.java).apply {
                        putExtra("place", location)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int = locationList.size
}
