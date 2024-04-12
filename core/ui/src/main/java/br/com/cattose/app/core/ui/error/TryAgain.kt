package br.com.cattose.app.core.ui.error

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.cattose.app.core.ui.R

@Composable
fun TryAgain(
    message: String,
    tryAgainActionText: String,
    modifier: Modifier = Modifier,
    onTryAgainClick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = (message),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onTryAgainClick() }) {
            Text(text = tryAgainActionText)
        }
    }
}

@Preview
@Composable
fun TryAgainPreview() {
    TryAgain(
        message = "Some error happened",
        tryAgainActionText = stringResource(id = R.string.action_retry)
    ) {}
}
