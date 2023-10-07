package su.iky.dtf.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import coil.compose.SubcomposeAsyncImage
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
    val localUriHandler = LocalUriHandler.current
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)
        .background(Color.DarkGray)
        .clickable {
            val subsiteId = entry.subsite.id
            val path = if (entry.subsite.url == "") "/u/$subsiteId" else entry.subsite.url
            val id = entry.id.toString()
            localUriHandler.openUri("https://dtf.ru$path/$id")
        }
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            RoundImageCard(model = entry.author.avatar, Modifier
                .padding(start = 6.dp, end = 6.dp, top = 6.dp, bottom = 6.dp)
                .size(48.dp))
            Column(horizontalAlignment = Alignment.Start) {
                Text( text = entry.author.name , fontWeight = FontWeight.Bold)
                Text( text = entry.subsite.name, fontSize = 12.sp)
            }

        }

        if(entry.title != "") {
            Text( text = entry.title , fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 6.dp, start = 6.dp, end = 6.dp, top = 2.dp))
        }



        if(entry.media.url != "") {
            if(entry.media.type == "image") {

                // TODO: Как то нужно снимать блюр при нажатии
                val imageBlur = if ( false /* entry.isBlur */ ) Modifier.blur(16.dp) else Modifier

                SubcomposeAsyncImage(
                    model = entry.media.url,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp)
                        .then(imageBlur),
                    contentScale = ContentScale.FillWidth,
                    loading = {
                        Skeleton(imageRatio = (entry.media.height / entry.media.width))
                    }
                )
            }else{
                AsyncVideo(
                    model = entry.media.url,
                    imageRatio = (entry.media.height / entry.media.width),
                    modifier = Modifier.padding(top = 2.dp),
                    preview = entry.media.preview
                )
            }
        }
    }
}
