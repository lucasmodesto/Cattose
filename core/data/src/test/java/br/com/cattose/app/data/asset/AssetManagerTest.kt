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

package br.com.cattose.app.data.asset

import android.content.Context
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import java.io.InputStream

class AssetManagerTest {

    private val context = mockk<Context>()
    private val sut = DefaultAssetManager(context)

    @Test
    fun `given filename when open is called then return inputstream`() {
        val expected = InputStream.nullInputStream()
        every { context.assets.open("file") } returns expected

        val fileInputStream = sut.open("file")

        Truth.assertThat(fileInputStream).isEqualTo(expected)
    }
}