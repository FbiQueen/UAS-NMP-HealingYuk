package com.example.healingyuk

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.healingyuk.databinding.ActivitySignUpBinding
import org.json.JSONObject

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUpSubmit.setOnClickListener {
            val name = binding.etNameSignUp.text.toString().trim()
            val email = binding.etEmailSignUp.text.toString().trim()
            val password = binding.etPasswordSignUp.text.toString().trim()
            val repeatPassword = binding.etRepeatPasswordSignUp.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != repeatPassword) {
                Toast.makeText(this, "Password tidak sama", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerUser(name, email, password, repeatPassword)
        }
    }

    private fun registerUser(name: String, email: String, password: String, repeatPassword: String) {
        val url = "http://10.0.2.2/healing/register.php"

        val request = object : StringRequest(Method.POST, url,
            { response ->
                val json = JSONObject(response)
                val success = json.getBoolean("success")
                val message = json.getString("message")
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

                if (success) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            },
            { error ->
                Toast.makeText(this, "Gagal koneksi: ${error.message}", Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["name"] = name
                params["email"] = email
                params["password"] = password
                params["repeat_password"] = repeatPassword
                return params
            }
        }

        Volley.newRequestQueue(this).add(request)
    }
}
