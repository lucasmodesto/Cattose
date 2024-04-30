@file:OptIn(ExperimentalSharedTransitionApi::class)

package br.com.cattose.app.feature.list.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.cattose.app.data.model.domain.CatImage
import br.com.cattose.app.feature.list.ListScreen

const val LIST_SCREEN_ROUTE = "list"

fun SharedTransitionScope.listScreen(
    navGraphBuilder: NavGraphBuilder,
    onItemClick: (CatImage) -> Unit
) {
    navGraphBuilder.apply {
        composable(
            route = LIST_SCREEN_ROUTE
        ) {
            ListScreen(onItemClick = onItemClick, animatedVisibilityScope = this)
        }
    }
}
