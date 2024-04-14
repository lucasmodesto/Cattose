package br.com.cattose.app.data.model.domain

data class Breed(
    val name: String,
    val description: String = "",
    val temperaments: List<String> = emptyList()
)
