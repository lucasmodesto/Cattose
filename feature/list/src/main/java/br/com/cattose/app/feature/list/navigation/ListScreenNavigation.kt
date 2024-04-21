package br.com.cattose.app.feature.list.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.cattose.app.data.model.domain.CatImage
import br.com.cattose.app.feature.list.ListScreen

const val LIST_SCREEN_ROUTE = "list"

fun NavGraphBuilder.listScreen(onItemClick: (CatImage) -> Unit) {
    composable(
        route = LIST_SCREEN_ROUTE
    ) {
        ListScreen(onItemClick = onItemClick)
    }
}
