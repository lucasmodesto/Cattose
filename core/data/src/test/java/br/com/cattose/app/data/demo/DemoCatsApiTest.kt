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

package br.com.cattose.app.data.demo

import JvmUnitTestDemoAssetManager
import br.com.cattose.app.data.api.DemoCatsApi
import br.com.cattose.app.data.model.response.BreedResponse
import br.com.cattose.app.data.model.response.CatDetailResponse
import br.com.cattose.app.data.model.response.CatImageResponse
import br.com.cattose.app.data.model.response.WeightResponse
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Test

class DemoCatsApiTest {

    private val sut = DemoCatsApi(
        json = Json { ignoreUnknownKeys = true },
        assetManager = JvmUnitTestDemoAssetManager
    )

    @Test
    fun `given local json when fetchList is called then should return deserialized object`() =
        runTest {
            val list = sut.fetchList(1)

            Truth.assertThat(list.first()).isEqualTo(
                CatImageResponse(
                    id = "N-94oSJ5T",
                    url = "https://cdn2.thecatapi.com/images/N-94oSJ5T.jpg"
                )
            )
        }

    @Test
    fun `given local json when fetchDetails is called then should return deserialized object`() =
        runTest {
            val details = sut.fetchDetails("id")

            Truth.assertThat(details).isEqualTo(
                CatDetailResponse(
                    id = "N-94oSJ5T",
                    url = "https://cdn2.thecatapi.com/images/N-94oSJ5T.jpg",
                    width = 1920,
                    height = 1280,
                    breeds = listOf(
                        BreedResponse(
                            id = "abys",
                            name = "Abyssinian",
                            cfaUrl = "http://cfa.org/Breeds/BreedsAB/Abyssinian.aspx",
                            vetstreetUrl = "http://www.vetstreet.com/cats/abyssinian",
                            vcahospitalsUrl = "https://vcahospitals.com/know-your-pet/cat-breeds/abyssinian",
                            temperament = "Active, Energetic, Independent, Intelligent, Gentle",
                            origin = "Egypt",
                            countryCodes = "EG",
                            countryCode = "EG",
                            description = "The Abyssinian is easy to care for, and a joy to have in your home. They’re affectionate cats and love both people and other animals.",
                            lifeSpan = "14 - 15",
                            indoor = 0,
                            lap = 1,
                            altNames = "",
                            adaptability = 5,
                            affectionLevel = 5,
                            childFriendly = 3,
                            dogFriendly = 4,
                            energyLevel = 5,
                            grooming = 1,
                            healthIssues = 2,
                            intelligence = 5,
                            sheddingLevel = 2,
                            socialNeeds = 5,
                            strangerFriendly = 5,
                            vocalisation = 1,
                            experimental = 0,
                            hairless = 0,
                            natural = 1,
                            rare = 0,
                            rex = 0,
                            suppressedTail = 0,
                            shortLegs = 0,
                            wikipediaUrl = "https://en.wikipedia.org/wiki/Abyssinian_(cat)",
                            hypoallergenic = 0,
                            referenceImageId = "0XYvRd7oD",
                            weight = WeightResponse(
                                imperial = "7  -  10",
                                metric = "3 - 5"
                            )
                        )
                    )
                )
            )
        }
}
