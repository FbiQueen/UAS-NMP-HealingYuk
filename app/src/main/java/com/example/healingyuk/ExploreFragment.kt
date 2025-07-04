package com.example.healingyuk

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.healingyuk.databinding.FragmentExploreBinding
import org.json.JSONArray
import org.json.JSONObject

class ExploreFragment : Fragment() {
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private val locationList = mutableListOf<Location>()
    private lateinit var adapter: LocationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    private lateinit var addLocationLauncher: ActivityResultLauncher<Intent>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = LocationAdapter(requireContext(), locationList)
        binding.rvExplore.layoutManager = LinearLayoutManager(requireContext())
        binding.rvExplore.adapter = adapter
        binding.fabAddLocation.setOnClickListener {
            val intent = Intent(requireContext(), NewLocationActivity::class.java)
            addLocationLauncher.launch(intent)
        }
        addLocationLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                locationList.clear()
                loadLocations()
            }
        }
        loadLocations()
    }

    private fun loadLocations() {
        val url = "http://10.0.2.2/healing/get_locations.php"

        val request = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("ExploreFragment", "Response: $response")

                try {
                    val json = JSONObject(response)
                    val jsonArray = json.getJSONArray("data")
                    locationList.clear()

                    for (i in 0 until jsonArray.length()) {
                        val obj = jsonArray.getJSONObject(i)
                        val location = Location(
                            id = obj.getInt("id"),
                            name = obj.getString("name"),
                            category = obj.getString("category"),
                            imageUrl = obj.getString("image_url"),
                            shortDescription = obj.getString("short_description"),
                            completeDescription = obj.getString("full_description")
                        )

                        locationList.add(location)
                    }

                    adapter.notifyDataSetChanged()
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Parsing error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            })

        Volley.newRequestQueue(requireContext()).add(request)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
