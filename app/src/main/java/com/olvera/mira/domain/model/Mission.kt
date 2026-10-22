package com.olvera.mira.domain.model

data class Mission(
    val id: String,
    val dayNumber: Int,
    val title: String,
    val hook: String,
    val instructions: String,
    val technique: String,
    val durationMinutes: Int
)
