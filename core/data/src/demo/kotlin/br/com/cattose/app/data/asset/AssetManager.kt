package br.com.cattose.app.data.asset

import java.io.InputStream

interface AssetManager {
    fun open(fileName: String): InputStream
}
