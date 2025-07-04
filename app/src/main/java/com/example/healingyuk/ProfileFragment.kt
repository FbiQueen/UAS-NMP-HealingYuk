package com.example.healingyuk

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.healingyuk.R
import com.example.healingyuk.SessionManager
import com.example.healingyuk.databinding.FragmentProfileBinding
import org.json.JSONObject

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        val user = SessionManager.getUser(requireContext())
        if (user != null) {
            binding.tvNameProfile.text = user.name
            binding.tvEmailProfile.text = user.email
            binding.tvJoinedSinceProfile.text = user.createdAt

            fetchFavoriteCount(user.id)
        } else {
            Toast.makeText(requireContext(), "User tidak ditemukan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchFavoriteCount(userId: Int) {
        val url = "http://10.0.2.2/healing/count_favorite.php"

        val request = object : StringRequest(Method.POST, url, { response ->
            val json = JSONObject(response)
            if (json.getBoolean("success")) {
                val count = json.getInt("count")
                binding.tvTotalFavoritesProfile.text = "$count lokasi"
            } else {
                binding.tvTotalFavoritesProfile.text = "0 lokasi"
            }
        }, {
            Toast.makeText(requireContext(), "Gagal memuat jumlah favorit", Toast.LENGTH_SHORT).show()
        }) {
            override fun getParams(): MutableMap<String, String> {
                return mutableMapOf("user_id" to userId.toString())
            }
        }

        Volley.newRequestQueue(requireContext()).add(request)
    }
    override fun onResume() {
        super.onResume()
        val user = SessionManager.getUser(requireContext())
        if (user != null) {
            fetchFavoriteCount(user.id)
        }
    }
}
