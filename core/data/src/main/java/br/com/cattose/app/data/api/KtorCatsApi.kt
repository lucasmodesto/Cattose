package br.com.cattose.app.data.api

import br.com.cattose.app.data.model.CatDetailResponse
import br.com.cattose.app.data.model.CatImageResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.parameters
import javax.inject.Inject

class KtorCatsApi @Inject constructor(
    private val httpClient: HttpClient
) : CatsApi {

    override suspend fun fetchList(): List<CatImageResponse> {
        return httpClient.get("images/search") {
            parameters {
                parameter("limit", 10)
                parameter("has_breeds", true)
            }
        }.body()
    }

    override suspend fun fetchDetails(id: String): CatDetailResponse {
        return httpClient.get("images/$id").body()
    }
}