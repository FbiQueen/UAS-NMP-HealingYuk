package com.example.healingyuk.fragments // Sesuaikan dengan package Anda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.healingyuk.databinding.FragmentExploreBinding


class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Inisialisasi RecyclerView, Adapter, dll. di sini
        // Misalnya:
        // binding.rvExplore.layoutManager = LinearLayoutManager(context)
        // binding.rvExplore.adapter = YourHealingPlaceAdapter(yourDataList)

        // FAB listener
        binding.fabAddLocation.setOnClickListener {
            // Logika untuk berpindah ke halaman tambah lokasi
            // Toast.makeText(context, "Add new location clicked!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}