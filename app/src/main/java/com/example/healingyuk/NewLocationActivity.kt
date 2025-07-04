package com.example.healingyuk

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.healingyuk.databinding.ActivityNewLocationBinding
import org.json.JSONObject

class NewLocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categories = listOf(
            "Cafe", "Resto", "Warkop", "Hotel",
            "Karaoke", "Arcade", "Playground", "Billiard", "Bowling", "Bar"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categories)
        binding.spinnerCategory.setAdapter(adapter)

        binding.btnAddLocation.setOnClickListener {
            submitLocation()
        }
    }

    private fun submitLocation() {
        val name = binding.etLocationName.text.toString().trim()
        val category = binding.spinnerCategory.text.toString().trim()
        val imageUrl = binding.etImageUrl.text.toString().trim()
        val shortDesc = binding.etShortDescription.text.toString().trim()
        val fullDesc = binding.etCompleteDescription.text.toString().trim()

        if (name.isEmpty() || category.isEmpty() || imageUrl.isEmpty() ||
            shortDesc.isEmpty() || fullDesc.isEmpty()
        ) {
            Toast.makeText(this, "Semua kolom wajib diisi", Toast.LENGTH_SHORT).show()
            return
        }

        val url = "http://10.0.2.2/healing/add_location.php"

        val request = object : StringRequest(Request.Method.POST, url,
            { response ->
                val json = JSONObject(response)
                if (json.getBoolean("success")) {
                    Toast.makeText(this, "Lokasi berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                return mutableMapOf(
                    "name" to name,
                    "image_url" to imageUrl,
                    "short_desc" to shortDesc,
                    "full_desc" to fullDesc,
                    "category" to category
                )
            }
        }

        Volley.newRequestQueue(this).add(request)
    }
}
