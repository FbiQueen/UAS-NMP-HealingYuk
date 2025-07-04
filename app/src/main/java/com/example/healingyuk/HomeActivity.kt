package com.example.healingyuk

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.healingyuk.databinding.ActivityHomeBinding
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = SessionManager.getUser(this)
        val userName = user?.name ?: "User"
        binding.navView.getHeaderView(0)
            .findViewById<TextView>(R.id.tvWelcomeUser).text = "Welcome, $userName"

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = listOf(
            ExploreFragment(),
            FavoriteFragment(),
            ProfileFragment()
        )

        val adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = fragments.size
            override fun createFragment(position: Int): Fragment = fragments[position]
        }

        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled = false

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.exploreFragment -> binding.viewPager.currentItem = 0
                R.id.favoriteFragment -> binding.viewPager.currentItem = 1
                R.id.profileFragment -> binding.viewPager.currentItem = 2
            }
            true
        }

        binding.navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_change_password -> {
                startActivity(Intent(this, ChangePasswordActivity::class.java))
            }
            R.id.nav_sign_out -> {
                SessionManager.clearUser(this)
                Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        binding.drawerLayout.openDrawer(GravityCompat.START)
        return true
    }
}
