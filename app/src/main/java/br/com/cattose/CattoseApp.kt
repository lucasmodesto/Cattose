package br.com.cattose

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.GifDecoder
import coil.disk.DiskCache
import coil.memory.MemoryCache
import dagger.hilt.android.HiltAndroidApp

private const val COIL_IMAGE_CACHE_DIR = "image_cache"
private const val DISK_CACHE_MAX_SIZE_BYTES: Long = 10 * 1024 * 1024
private const val MEMORY_CACHE_MAX_SIZE_PERCENTAGE = 0.2

@HiltAndroidApp
class CattoseApp : Application(), ImageLoaderFactory {

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(MEMORY_CACHE_MAX_SIZE_PERCENTAGE)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve(COIL_IMAGE_CACHE_DIR))
                    .maxSizeBytes(DISK_CACHE_MAX_SIZE_BYTES)
                    .build()
            }.components {
                add(GifDecoder.Factory())
            }
            .respectCacheHeaders(false)
            .build()
    }
}