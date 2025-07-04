package com.example.healingyuk

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.healingyuk.databinding.FragmentFavoriteBinding
import org.json.JSONObject

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    private lateinit var binding: FragmentFavoriteBinding
    private val favList = mutableListOf<Location>()
    private lateinit var adapter: LocationFavoriteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)

        adapter = LocationFavoriteAdapter(requireContext(), favList)
        binding.rvFavorite.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavorite.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        loadFavorites()
    }

    private fun loadFavorites() {
        val user = SessionManager.getUser(requireContext()) ?: return
        val url = "http://10.0.2.2/healing/get_favorite.php"
        val queue = Volley.newRequestQueue(requireContext())

        val request = object : StringRequest(Method.POST, url,
            { response ->
                try {
                    val json = JSONObject(response)
                    favList.clear()
                    if (json.getBoolean("success")) {
                        val data = json.getJSONArray("data")
                        for (i in 0 until data.length()) {
                            val obj = data.getJSONObject(i)
                            val location = Location(
                                id = obj.getInt("id"),
                                name = obj.getString("name"),
                                category = obj.getString("category"),
                                imageUrl = obj.getString("image_url"),
                                shortDescription = obj.getString("short_desc"),
                                completeDescription = obj.getString("full_desc")
                            )
                            favList.add(location)
                        }
                        adapter.notifyDataSetChanged()
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            },
            { error ->
                Toast.makeText(requireContext(), "Gagal memuat favorit: ${error.message}", Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                return mutableMapOf("user_id" to user.id.toString())
            }
        }
        queue.add(request)
    }
}
