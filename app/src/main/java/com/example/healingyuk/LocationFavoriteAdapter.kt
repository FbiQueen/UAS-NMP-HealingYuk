package com.example.healingyuk

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.healingyuk.databinding.ItemHealingPlaceFavoriteBinding
import com.squareup.picasso.Picasso

class LocationFavoriteAdapter(
    private val context: Context,
    private val locations: List<Location>
) : RecyclerView.Adapter<LocationFavoriteAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemHealingPlaceFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(place: Location) {
            binding.tvPlaceNameFavorite.text = place.name
            binding.tvPlaceCategoryFavorite.text = place.category
            Picasso.get().load(place.imageUrl).into(binding.ivPlaceImageFavorite)

            binding.root.setOnClickListener {
                val intent = Intent(context, HealingDetailsActivity::class.java)
                intent.putExtra("place", place)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHealingPlaceFavoriteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = locations.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(locations[position])
    }
}
