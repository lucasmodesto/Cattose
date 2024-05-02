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

package br.com.cattose.app.core.ui.tags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import br.com.cattose.app.core.ui.theme.CattoTheme

@Composable
fun Tag(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    contentColor: Color = MaterialTheme.colorScheme.onSecondary
) {
    Box(
        modifier = modifier.background(
            backgroundColor,
            RoundedCornerShape(8.dp)
        )
    ) {
        Text(
            text = text.replaceFirstChar(Char::titlecase),
            style = MaterialTheme.typography.labelSmall,
            color = contentColor,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagList(
    tags: List<String>,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        tags.forEach {
            Box(modifier = Modifier.padding(top = 8.dp)) {
                Tag(text = it, modifier = Modifier.padding(horizontal = 4.dp))
            }
        }
    }
}

@PreviewLightDark
@Composable
fun TagPreview() {
    CattoTheme {
        Surface {
            Tag(text = "Kitten")
        }
    }
}

@PreviewLightDark
@Composable
fun TagListPreview() {
    val tags = listOf(
        "kitten",
        "several",
        "gif",
        "pc",
        "gaming",
        "kitten",
        "several",
        "gif",
        "pc",
        "gaming",
        "kitten",
        "several",
        "gif",
        "pc",
        "gaming",
    )

    CattoTheme {
        Surface {
            TagList(tags = tags)
        }
    }
}
