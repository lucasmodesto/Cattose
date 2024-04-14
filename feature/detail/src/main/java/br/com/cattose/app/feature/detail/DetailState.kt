package br.com.cattose.app.feature.detail

import br.com.cattose.app.data.model.domain.CatDetails

sealed interface DetailState {
    data object Loading : DetailState
    data object Error : DetailState
    data class Success(val cat: CatDetails) : DetailState
}
