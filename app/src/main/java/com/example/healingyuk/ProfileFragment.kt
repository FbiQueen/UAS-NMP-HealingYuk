package com.example.healingyuk.fragments // Sesuaikan dengan package Anda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.healingyuk.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Data user akan dimuat dari database dan ditampilkan di sini
        // Untuk sementara, kita menggunakan teks statis seperti di layout
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}