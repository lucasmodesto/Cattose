package br.com.cattose.app.data.api

import br.com.cattose.app.data.model.response.CatDetailResponse
import br.com.cattose.app.data.model.response.CatImageResponse

interface CatsApi {
    suspend fun fetchList(page: Int): List<CatImageResponse>
    suspend fun fetchDetails(id: String): CatDetailResponse
}