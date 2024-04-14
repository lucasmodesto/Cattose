package br.com.cattose.app.data.mapper

import br.com.cattose.app.data.model.domain.CatDetails
import br.com.cattose.app.data.model.response.CatDetailResponse

fun CatDetailResponse.toDomain() = CatDetails(
    id = id,
    mainBreed = breeds?.map { it.toDomain() }?.firstOrNull(),
    imageUrl = url
)