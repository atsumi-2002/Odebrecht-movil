package com.example.odebrecht.model

data class Cita (
    val id: Long,
    val fecha: String,
    val user_message: String,
    val professional_id: Long,
    val user_id: Long
)