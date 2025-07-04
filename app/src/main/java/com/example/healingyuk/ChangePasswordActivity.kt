package com.example.healingyuk

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.healingyuk.databinding.ActivityChangePasswordBinding
import org.json.JSONObject

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnChangePasswordSubmit.setOnClickListener {
            val oldPassword = binding.etOldPassword.text.toString()
            val newPassword = binding.etNewPassword.text.toString()
            val repeatPassword = binding.etRepeatNewPassword.text.toString()

            if (oldPassword.isEmpty() || newPassword.isEmpty() || repeatPassword.isEmpty()) {
                Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPassword != repeatPassword) {
                Toast.makeText(this, "Password baru tidak cocok", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            changePassword(oldPassword, newPassword)
        }
    }

    private fun changePassword(oldPassword: String, newPassword: String) {
        val user = SessionManager.getUser(this)
        if (user == null) {
            Toast.makeText(this, "Sesi user tidak ditemukan", Toast.LENGTH_SHORT).show()
            return
        }

        val url = "http://10.0.2.2/healing/change_password.php"
        val request = object : StringRequest(
            Request.Method.POST, url,
            { response ->
                val json = JSONObject(response)
                Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show()
                if (json.getBoolean("success")) {
                    finish()
                }
            },
            { error ->
                Toast.makeText(this, "Gagal: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                return mutableMapOf(
                    "user_id" to user.id.toString(),
                    "old_password" to oldPassword,
                    "new_password" to newPassword
                )
            }
        }

        Volley.newRequestQueue(this).add(request)
    }
}
