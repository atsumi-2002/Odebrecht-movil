package com.example.odebrecht.model

data class Profesional(
    val id: Long,
    val address: String,
    val description: String,
    val email: String,
    val first_name: String,
    val image: String,
    val last_name: String,
    val licensed: String,
    val local_number: Int,
    val type: String
)