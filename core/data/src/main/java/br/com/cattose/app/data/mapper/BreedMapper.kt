package br.com.cattose.app.data.mapper

import br.com.cattose.app.data.model.BreedResponse
import br.com.cattose.app.core.domain.model.Breed

fun BreedResponse.mapToDomain() = Breed(
    description = description.orEmpty(),
    name = name,
    temperaments = temperament?.split(",")?.map { it.trim() } ?: emptyList()
)
