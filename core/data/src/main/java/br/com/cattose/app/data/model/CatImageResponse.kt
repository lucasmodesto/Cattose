package br.com.cattose.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatImageResponse(
    @SerialName("id") val id: String? = null,
    @SerialName("url") val url: String? = null
)
