package br.com.cattose.app.feature.detail

import br.com.cattose.app.data.model.domain.CatDetails

data class DetailState(
    val isLoading: Boolean = true,
    val hasError: Boolean = false,
    val catDetails: CatDetails? = null,
    val catImageUrl: String = ""
)
