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

package br.com.cattose.app.core.ui.preview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import br.com.cattose.app.core.ui.theme.CattoTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionPreviewTheme(
    content: @Composable SharedTransitionScope.(AnimatedVisibilityScope) -> Unit
) {
    CattoTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true, label = "") {
                content(this)
            }
        }
    }
}
