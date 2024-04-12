package br.com.cattose.app.data.api

import br.com.cattose.app.data.model.CatDetailResponse
import br.com.cattose.app.data.model.CatImageResponse

interface CatsApi {
    suspend fun fetchList(): List<CatImageResponse>
    suspend fun fetchDetails(id: String): CatDetailResponse
}