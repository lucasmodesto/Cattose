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

package br.com.cattose.app.data.api

import com.google.common.truth.Truth
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Test

class KtorCatsApiTest {

    private val defaultHeaders = headersOf(HttpHeaders.ContentType, "application/json")

    private val mockEngine = MockEngine { request ->
        when (request.url.encodedPath) {
            "/images/search" ->
                respond(
                    content = ByteReadChannel(imagesSearchJson),
                    status = HttpStatusCode.OK,
                    headers = defaultHeaders
                )

            "/images/id" ->
                respond(
                    content = ByteReadChannel(detailsJson),
                    status = HttpStatusCode.OK,
                    headers = defaultHeaders
                )

            else ->
                respond(
                    content = ByteReadChannel.Empty,
                    status = HttpStatusCode.BadRequest
                )
        }
    }

    private val sut = KtorCatsApi(HttpClient(mockEngine) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    })

    @Test
    fun `given response when fetchList is called then should return value`() = runTest {
        val response = sut.fetchList(1)

        Truth.assertThat(response).hasSize(2)
    }

    @Test
    fun `given response when fetchDetails is called then should return value`() = runTest {
        val response = sut.fetchDetails("id")

        Truth.assertThat(response.breeds?.first()!!.name).isEqualTo("Abyssinian")
    }
}
