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

package br.com.cattose.app.core.ui.image

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.transform.Transformation

@Composable
fun DefaultAsyncImage(
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    loadingPlaceholder: @Composable () -> Unit = {},
    errorPlaceholder: @Composable () -> Unit = {},
    contentScale: ContentScale = ContentScale.None,
    contentDescription: String? = null,
    transformations: List<Transformation>? = null,
    crossfade: Boolean = true
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .diskCacheKey(imageUrl)
            .memoryCacheKey(imageUrl)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .apply {
                transformations?.let {
                    transformations(it)
                }
            }
            .crossfade(crossfade)
            .build(),
        loading = {
            loadingPlaceholder()
        },
        error = {
            errorPlaceholder()
        },
        contentScale = contentScale,
        modifier = modifier,
        contentDescription = contentDescription
    )
}

@Composable
fun ImagePlaceholder(
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    @DrawableRes drawable: Int,
    contentScale: ContentScale = ContentScale.Crop,
    transformations: List<Transformation>? = null
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(drawable)
            .apply {
                transformations?.let {
                    transformations(it)
                }
            }
            .build(),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier
    )
}
