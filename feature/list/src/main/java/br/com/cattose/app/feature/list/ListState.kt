package br.com.cattose.app.feature.list

import br.com.cattose.app.core.domain.model.CatImage

sealed interface ListState {
    data object Loading : ListState
    data object Error : ListState
    data object Empty : ListState
    data class Success(val data: List<CatImage>) : ListState
}
