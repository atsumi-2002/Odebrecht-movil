package com.example.odebrecht.model

data class Consulta(
    val id: Long,
    val fecha: String,
    val medico_id: Long,
    val resultado: String,
    val usuario_id: Long
)