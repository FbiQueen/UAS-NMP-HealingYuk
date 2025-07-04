package com.example.healingyuk

import java.io.Serializable

data class HealingPlace(
    val id: Int,
    val name: String,
    val category: String,
    val image_url: String,
    val short_description: String,
    val description: String
) : Serializable
