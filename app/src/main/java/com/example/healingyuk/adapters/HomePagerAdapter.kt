package com.example.healingyuk.adapters // Sesuaikan ini dengan package adapters Anda

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.healingyuk.fragments.ExploreFragment // Sesuaikan ini dengan package fragments Anda
import com.example.healingyuk.fragments.FavoriteFragment // Sesuaikan ini dengan package fragments Anda
import com.example.healingyuk.fragments.ProfileFragment // Sesuaikan ini dengan package fragments Anda

// HomePagerAdapter akan bertanggung jawab untuk menyediakan Fragment ke ViewPager2
class HomePagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    // getItemCount() mengembalikan jumlah total halaman (Fragment) yang akan ditampilkan di ViewPager2.
    // Sesuai dengan spesifikasi, kita memiliki 3 fragment: Explore, Favorite, Profile.
    override fun getItemCount(): Int {
        return 3
    }

    // createFragment() dipanggil oleh ViewPager2 ketika perlu menampilkan Fragment di posisi tertentu.
    // Logika di sini menentukan Fragment mana yang akan dikembalikan berdasarkan 'position' (indeks halaman).
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ExploreFragment()   // Posisi 0 adalah Fragment Explore
            1 -> FavoriteFragment()  // Posisi 1 adalah Fragment Favorite
            2 -> ProfileFragment()   // Posisi 2 adalah Fragment Profile
            else -> throw IllegalArgumentException("Posisi tidak valid: $position")
        }
    }
}