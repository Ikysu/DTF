package su.iky.dtf


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.lifecycle.ViewModelProvider
import su.iky.dtf.ui.components.EntryList
import su.iky.dtf.ui.theme.DTFTheme
import su.iky.dtf.ui.viewmodel.FeedView


class MainActivity : ComponentActivity() {

    private lateinit var feedView: FeedView



    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        feedView = ViewModelProvider(this)[FeedView::class.java]

        feedView.getEntries()

        setContent {
            DTFTheme {
                Scaffold {
                    EntryList(liveEntries = feedView.liveEntries, feedView)
                }
            }
        }
    }
}

