package com.example.healingyuk

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.healingyuk.databinding.ItemHealingPlaceBinding
import com.squareup.picasso.Picasso

class ExploreAdapter(
    private val context: Context,
    private val list: List<HealingPlace>,
    private val onReadMoreClick: (HealingPlace) -> Unit
) : RecyclerView.Adapter<ExploreAdapter.HealingViewHolder>() {

    inner class HealingViewHolder(val binding: ItemHealingPlaceBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealingViewHolder {
        val binding = ItemHealingPlaceBinding.inflate(LayoutInflater.from(context), parent, false)
        return HealingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HealingViewHolder, position: Int) {
        val place = list[position]
        with(holder.binding) {
            tvPlaceName.text = place.name
            tvPlaceCategory.text = place.category
            tvShortDescription.text = place.short_description
            Picasso.get().load(place.image_url).into(ivPlaceImage)

            btnReadMore.setOnClickListener {
                onReadMoreClick(place)
            }
        }
    }

    override fun getItemCount(): Int = list.size
}
