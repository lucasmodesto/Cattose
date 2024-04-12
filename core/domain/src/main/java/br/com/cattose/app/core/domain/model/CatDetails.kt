package br.com.cattose.app.core.domain.model

data class CatDetails(
    val id: String,
    val imageUrl: String,
    val mainBreed: Breed?
)
