package br.com.cattose.app.data.mapper

import br.com.cattose.app.data.model.CatImageResponse
import br.com.cattose.app.core.domain.model.CatImage

fun CatImageResponse.mapToDomain() = CatImage(
    id = id.orEmpty(),
    imageUrl = url.orEmpty()
)
