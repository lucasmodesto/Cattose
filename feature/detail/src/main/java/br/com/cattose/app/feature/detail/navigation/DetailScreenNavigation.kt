package br.com.cattose.app.feature.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.cattose.app.feature.detail.DetailScreen

const val CAT_ID_ARG = "catId"
const val DETAIL_SCREEN_ROUTE = "detail"

fun NavController.navigateToDetail(
    catId: String,
    navOptions: NavOptions? = null
) {
    navigate("$DETAIL_SCREEN_ROUTE/$catId", navOptions)
}

fun NavGraphBuilder.detailScreen(onBackClick: () -> Unit) {
    composable(
        route = "$DETAIL_SCREEN_ROUTE/{$CAT_ID_ARG}"
    ) {
        DetailScreen(onBackClick = onBackClick)
    }
}
