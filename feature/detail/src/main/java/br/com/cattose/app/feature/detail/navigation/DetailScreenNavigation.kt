/*
 * Designed and developed by 2024 lucasmodesto (Lucas Modesto)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.cattose.app.feature.detail.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.cattose.app.feature.detail.DetailScreen

const val CAT_ID_ARG = "catId"
const val IMAGE_URL_ARG = "imageUrl"
const val DETAIL_SCREEN_ROUTE = "detail"

fun NavController.navigateToDetail(
    catId: String,
    imageUrl: String,
    navOptions: NavOptions? = null
) {
    navigate("$DETAIL_SCREEN_ROUTE/?$CAT_ID_ARG=$catId&$IMAGE_URL_ARG=$imageUrl", navOptions)
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun SharedTransitionScope.detailScreen(navGraphBuilder: NavGraphBuilder, onBackClick: () -> Unit) {
    navGraphBuilder.apply {
        composable(
            route = "$DETAIL_SCREEN_ROUTE/?$CAT_ID_ARG={$CAT_ID_ARG}&$IMAGE_URL_ARG={$IMAGE_URL_ARG}",
            arguments = listOf(
                navArgument(CAT_ID_ARG) {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument(IMAGE_URL_ARG) {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) {
            DetailScreen(onBackClick = onBackClick, animatedVisibilityScope = this)
        }
    }
}
