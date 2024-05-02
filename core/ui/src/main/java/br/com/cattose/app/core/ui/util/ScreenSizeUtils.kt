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

package br.com.cattose.app.core.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private const val HALF_VALUE_DIVIDER = 2

@Composable
fun halfScreenHeightDp(): Dp {
    return LocalConfiguration.current.screenHeightDp.dp.div(HALF_VALUE_DIVIDER)
}

@Composable
fun halfScreenWidthDp(): Dp {
    return LocalConfiguration.current.screenWidthDp.dp.div(HALF_VALUE_DIVIDER)
}
