package su.iky.dtf.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun RoundImageCard(
    model: String, modifier: Modifier = Modifier
) {
    Card(shape = CircleShape, modifier = modifier) {
        AsyncImage(
            model = model,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}