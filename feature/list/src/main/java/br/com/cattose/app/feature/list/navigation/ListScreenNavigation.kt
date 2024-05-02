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
