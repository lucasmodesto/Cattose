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
        Truth.assertThat(response.first().id).isEqualTo("N-94oSJ5T")
        Truth.assertThat(response.first().url)
            .isEqualTo("https://cdn2.thecatapi.com/images/N-94oSJ5T.jpg")
    }

    @Test
    fun `given response when fetchDetails is called then should return value`() = runTest {
        val response = sut.fetchDetails("id")

        val breed = response.breeds!!.first()
        Truth.assertThat(response.id).isEqualTo("N-94oSJ5T")
        Truth.assertThat(response.url).isEqualTo("https://cdn2.thecatapi.com/images/N-94oSJ5T.jpg")
        Truth.assertThat(response.width).isEqualTo(1920)
        Truth.assertThat(response.height).isEqualTo(1280)
        Truth.assertThat(breed.name).isEqualTo("Abyssinian")
        Truth.assertThat(breed.id).isEqualTo("abys")
        Truth.assertThat(breed.cfaUrl).isEqualTo("http://cfa.org/Breeds/BreedsAB/Abyssinian.aspx")
        Truth.assertThat(breed.vetstreetUrl).isEqualTo("http://www.vetstreet.com/cats/abyssinian")
        Truth.assertThat(breed.vcahospitalsUrl)
            .isEqualTo("https://vcahospitals.com/know-your-pet/cat-breeds/abyssinian")
        Truth.assertThat(breed.temperament)
            .isEqualTo("Active, Energetic, Independent, Intelligent, Gentle")
        Truth.assertThat(breed.origin).isEqualTo("Egypt")
        Truth.assertThat(breed.countryCodes).isEqualTo("EG")
        Truth.assertThat(breed.countryCode).isEqualTo("EG")
        Truth.assertThat(breed.description)
            .isEqualTo(
                "The Abyssinian is easy to care for, and a joy to have in your home. " +
                        "They’re affectionate cats and love both people and other animals."
            )
        Truth.assertThat(breed.lifeSpan).isEqualTo("14 - 15")
        Truth.assertThat(breed.indoor).isEqualTo(0)
        Truth.assertThat(breed.lap).isEqualTo(1)
        Truth.assertThat(breed.altNames).isEqualTo("")
        Truth.assertThat(breed.adaptability).isEqualTo(5)
        Truth.assertThat(breed.affectionLevel).isEqualTo(5)
        Truth.assertThat(breed.childFriendly).isEqualTo(3)
        Truth.assertThat(breed.dogFriendly).isEqualTo(4)
        Truth.assertThat(breed.energyLevel).isEqualTo(5)
        Truth.assertThat(breed.grooming).isEqualTo(1)
        Truth.assertThat(breed.healthIssues).isEqualTo(2)
        Truth.assertThat(breed.intelligence).isEqualTo(5)
        Truth.assertThat(breed.sheddingLevel).isEqualTo(2)
        Truth.assertThat(breed.socialNeeds).isEqualTo(5)
        Truth.assertThat(breed.strangerFriendly).isEqualTo(5)
        Truth.assertThat(breed.vocalisation).isEqualTo(1)
        Truth.assertThat(breed.experimental).isEqualTo(0)
        Truth.assertThat(breed.hairless).isEqualTo(0)
        Truth.assertThat(breed.natural).isEqualTo(1)
        Truth.assertThat(breed.rare).isEqualTo(0)
        Truth.assertThat(breed.rex).isEqualTo(0)
        Truth.assertThat(breed.suppressedTail).isEqualTo(0)
        Truth.assertThat(breed.shortLegs).isEqualTo(0)
        Truth.assertThat(breed.wikipediaUrl)
            .isEqualTo("https://en.wikipedia.org/wiki/Abyssinian_(cat)")
        Truth.assertThat(breed.hypoallergenic).isEqualTo(0)
        Truth.assertThat(breed.referenceImageId).isEqualTo("0XYvRd7oD")
        Truth.assertThat(breed.weight?.imperial).isEqualTo("7  -  10")
        Truth.assertThat(breed.weight?.metric).isEqualTo("3 - 5")
    }
}
