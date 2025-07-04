package com.example.healingyuk

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.healingyuk.databinding.ActivityHealingDetailsBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject

class HealingDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHealingDetailsBinding
    private lateinit var place: Location
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealingDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        place = intent.getSerializableExtra("place") as? Location ?: run {
            Toast.makeText(this, "Data tempat tidak valid", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        showPlaceDetail(place)
        checkIfFavorite()

        binding.btnAddRemoveFavorite.setOnClickListener {
            handleFavoriteButton()
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun showPlaceDetail(place: Location) {
        binding.tvDetailName.text = place.name
        binding.tvDetailCategory.text = place.category
        binding.tvDetailDescription.text = place.completeDescription
        Picasso.get().load(place.imageUrl).into(binding.ivDetailImage)
    }

    private fun checkIfFavorite() {
        val user = SessionManager.getUser(this) ?: return
        val url = "http://10.0.2.2/healing/check_favorite.php"

        val request = object : StringRequest(Request.Method.POST, url,
            { response ->
                val json = JSONObject(response)
                if (json.getBoolean("success")) {
                    isFavorite = json.getBoolean("is_favorite")
                    updateFavoriteButton()
                }
            },
            {
                Toast.makeText(this, "Gagal memeriksa favorit", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                return mutableMapOf(
                    "user_id" to user.id.toString(),
                    "location_id" to place.id.toString()
                )
            }
        }

        Volley.newRequestQueue(this).add(request)
    }

    private fun handleFavoriteButton() {
        if (isFavorite) {
            removeFromFavorite()
        } else {
            addToFavorite()
        }
    }

    private fun addToFavorite() {
        val user = SessionManager.getUser(this) ?: return
        val url = "http://10.0.2.2/healing/add_favorite.php"

        val request = object : StringRequest(Request.Method.POST, url,
            { response ->
                val json = JSONObject(response)
                Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show()
                if (json.getBoolean("success")) {
                    isFavorite = true
                    updateFavoriteButton()
                }
            },
            { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                return mutableMapOf(
                    "user_id" to user.id.toString(),
                    "location_id" to place.id.toString()
                )
            }
        }

        Volley.newRequestQueue(this).add(request)
    }

    private fun removeFromFavorite() {
        val user = SessionManager.getUser(this) ?: return
        val url = "http://10.0.2.2/healing/remove_favorite.php"

        val request = object : StringRequest(Request.Method.POST, url,
            { response ->
                val json = JSONObject(response)
                Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show()
                if (json.getBoolean("success")) {
                    isFavorite = false
                    updateFavoriteButton()
                    finish()
                }
            },
            { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                return mutableMapOf(
                    "user_id" to user.id.toString(),
                    "location_id" to place.id.toString()
                )
            }
        }

        Volley.newRequestQueue(this).add(request)
    }

    private fun updateFavoriteButton() {
        binding.btnAddRemoveFavorite.text =
            if (isFavorite) "Remove Favourite" else "Add to Favourite"
    }
}