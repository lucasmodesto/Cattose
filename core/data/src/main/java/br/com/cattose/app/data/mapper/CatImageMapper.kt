package br.com.cattose.app.data.mapper

import br.com.cattose.app.data.model.domain.CatImage
import br.com.cattose.app.data.model.response.CatImageResponse

fun CatImageResponse.toDomain() = CatImage(
    id = id.orEmpty(),
    imageUrl = url.orEmpty()
)
