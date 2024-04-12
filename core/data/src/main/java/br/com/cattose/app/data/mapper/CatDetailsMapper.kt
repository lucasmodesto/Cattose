package br.com.cattose.app.data.mapper

import br.com.cattose.app.data.model.CatDetailResponse
import br.com.cattose.app.core.domain.model.CatDetails

fun CatDetailResponse.mapToDomain() = CatDetails(
    id = id,
    mainBreed = breeds?.map { it.mapToDomain() }?.firstOrNull(),
    imageUrl = url
)