package com.example.healingyuk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.healingyuk.adapters.HomePagerAdapter
import com.example.healingyuk.databinding.ActivityHomeBinding // Ini akan di-generate otomatis setelah build

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager = binding.viewPager
        val bottomNavigationView = binding.bottomNavigationView

        val pagerAdapter = HomePagerAdapter(this)
        viewPager.adapter = pagerAdapter

        // Menghubungkan BottomNavigationView dengan ViewPager2
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.exploreFragment -> viewPager.currentItem = 0
                R.id.favoriteFragment -> viewPager.currentItem = 1
                R.id.profileFragment -> viewPager.currentItem = 2
            }
            true
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bottomNavigationView.menu.getItem(position).isChecked = true
            }
        })
    }
}