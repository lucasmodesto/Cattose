package br.com.cattose.app.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatDetailResponse(
    @SerialName("id") val id: String,
    @SerialName("url") val url: String,
    @SerialName("breeds") val breeds: List<BreedResponse>?,
    @SerialName("width") val width: Int,
    @SerialName("height") val height: Int
)
