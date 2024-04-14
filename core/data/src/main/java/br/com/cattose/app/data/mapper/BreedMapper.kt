package br.com.cattose.app.data.mapper

import br.com.cattose.app.data.model.response.BreedResponse
import br.com.cattose.app.data.model.domain.Breed

fun BreedResponse.toDomain() = Breed(
    description = description.orEmpty(),
    name = name,
    temperaments = temperament?.split(",")?.map { it.trim() } ?: emptyList()
)
