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
    imageUrl: String? = null,
    modifier: Modifier = Modifier,
    loadingPlaceholder: @Composable () -> Unit = {},
    errorPlaceholder: @Composable () -> Unit = {},
    contentScale: ContentScale = ContentScale.None,
    contentDescription: String? = null,
    transformations: List<Transformation>? = null
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
            .crossfade(true)
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
