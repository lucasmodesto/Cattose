package br.com.cattose.app.data.asset

import android.content.Context
import java.io.InputStream
import javax.inject.Inject

class DefaultAssetManager @Inject constructor(
    private val context: Context
) : AssetManager {
    override fun open(fileName: String): InputStream =
        context.assets.open(fileName)
}
