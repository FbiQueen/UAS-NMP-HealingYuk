package com.example.healingyuk

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.healingyuk.databinding.ActivityMainBinding
import com.example.healingyuk.User
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)

        if (SessionManager.getUser(this) != null) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        binding.btnSignInSubmit.setOnClickListener {
            val username = binding.etEmailSignIn.text.toString().trim()
            val password = binding.etPasswordSignIn.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Isi semua kolom", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(username, password)
            }
        }

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun loginUser(email: String, password: String) {
        val url = "http://10.0.2.2/healing/login.php"

        val request = object : StringRequest(
            Method.POST, url,
            { response ->
                val json = JSONObject(response)
                if (json.getBoolean("success")) {
                    val userJson = json.getJSONObject("data")
                    val user = User(
                        id = userJson.getInt("id"),
                        name = userJson.getString("name"),
                        email = userJson.getString("email"),
                        createdAt = userJson.getString("created_at")
                    )
                    SessionManager.saveUser(this, user)
                    Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(this, "Terjadi kesalahan: ${error.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                return mutableMapOf(
                    "email" to email,
                    "password" to password
                )
            }
        }
        Volley.newRequestQueue(this).add(request)
    }
}
