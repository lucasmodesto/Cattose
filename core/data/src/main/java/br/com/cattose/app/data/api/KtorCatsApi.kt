package br.com.cattose.app.data.api

import br.com.cattose.app.data.model.response.CatDetailResponse
import br.com.cattose.app.data.model.response.CatImageResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.parameters
import javax.inject.Inject

class KtorCatsApi @Inject constructor(
    private val httpClient: HttpClient
) : CatsApi {

    override suspend fun fetchList(page: Int): List<CatImageResponse> {
        return httpClient.get("images/search") {
            parameters {
                parameter("limit", 10)
                parameter("page", page)
                parameter("has_breeds", true)
            }
        }.body()
    }

    override suspend fun fetchDetails(id: String): CatDetailResponse {
        return httpClient.get("images/$id").body()
    }
}