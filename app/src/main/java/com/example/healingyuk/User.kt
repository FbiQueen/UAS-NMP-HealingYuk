package com.example.healingyuk

import java.io.Serializable

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val createdAt: String
) : Serializable


