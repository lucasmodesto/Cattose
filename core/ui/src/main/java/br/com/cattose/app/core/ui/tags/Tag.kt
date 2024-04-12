package br.com.cattose.app.core.ui.tags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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

@Preview
@Composable
fun TagPreview() {
    Tag(text = "Kitten")
}

@Preview
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
    TagList(tags = tags)

}
