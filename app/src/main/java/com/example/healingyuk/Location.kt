package com.example.healingyuk

import java.io.Serializable

data class Location(
    val id: Int,
    val name: String,
    val category: String,
    val imageUrl: String,
    val shortDescription: String,
    val completeDescription: String
) : Serializable