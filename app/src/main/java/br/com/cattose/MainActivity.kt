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

package br.com.cattose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.cattose.app.core.ui.theme.CattoTheme
import br.com.cattose.app.feature.detail.DetailScreen
import br.com.cattose.app.feature.detail.navigation.DetailRoute
import br.com.cattose.app.feature.list.ListScreen
import br.com.cattose.app.feature.list.navigation.ListRoute
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalSharedTransitionApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CattoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    SharedTransitionLayout {
                        NavHost(
                            navController = navController,
                            startDestination = ListRoute,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            composable<ListRoute> {
                                ListScreen(onItemClick = {
                                    navController.navigate(
                                        DetailRoute(
                                            id = it.id,
                                            imageUrl = it.imageUrl
                                        )
                                    )
                                }, animatedVisibilityScope = this)
                            }
                            composable<DetailRoute> {
                                DetailScreen(
                                    onBackClick = {
                                        navController.navigateUp()
                                    },
                                    animatedVisibilityScope = this
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
