package br.com.cattose.app.core.domain.model

data class Breed(
    val name: String,
    val description: String = "",
    val temperaments: List<String> = emptyList()
)
