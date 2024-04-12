package br.com.cattose.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeightResponse(
    @SerialName("imperial") val imperial: String?,
    @SerialName("metric") val metric: String?
)
