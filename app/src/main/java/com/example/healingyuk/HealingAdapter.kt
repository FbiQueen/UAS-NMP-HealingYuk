package com.example.healingyuk
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.healingyuk.HealingDetailsActivity
import com.example.healingyuk.databinding.ItemHealingPlaceBinding
import com.example.healingyuk.HealingPlace
import com.squareup.picasso.Picasso

class HealingAdapter(
    private val context: Context,
    private val places: List<HealingPlace>
) : RecyclerView.Adapter<HealingAdapter.HealingViewHolder>() {

    inner class HealingViewHolder(val binding: ItemHealingPlaceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealingViewHolder {
        val binding = ItemHealingPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HealingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HealingViewHolder, position: Int) {
        val place = places[position]
        holder.binding.tvPlaceName.text = place.name
        holder.binding.tvPlaceCategory.text = place.category
        holder.binding.tvShortDescription.text = place.short_description
        Picasso.get().load(place.image_url).into(holder.binding.ivPlaceImage)

        holder.binding.btnReadMore.setOnClickListener {
            val intent = Intent(context, HealingDetailsActivity::class.java)
            intent.putExtra("place", place)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = places.size
}
