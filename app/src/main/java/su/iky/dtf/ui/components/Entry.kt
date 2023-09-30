package su.iky.dtf.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import coil.compose.AsyncImage
import su.iky.dtf.model.Entry
import su.iky.dtf.ui.viewmodel.FeedView

@Composable
fun EntryList(liveEntries: LiveData<List<Entry>>, feedView: FeedView) {
    val entries by liveEntries.observeAsState(initial = emptyList() )

    LazyColumn {
        itemsIndexed(items = entries) { index, entry ->

            if (index == entries.lastIndex) {
                feedView.getEntries()
            }

            EntryItem(entry)
        }
    }
}

@Composable
fun EntryItem(entry: Entry) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .background(Color.DarkGray)) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            RoundImageCard(model = entry.author.avatar, Modifier
                .padding(4.dp)
                .size(48.dp))
            Text( text = entry.author.name , fontWeight = FontWeight.Bold)
        }

        AsyncImage(model = entry.media.preview, contentDescription = null, modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.FillWidth)
    }
}
