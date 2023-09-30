package su.iky.dtf.ui.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import su.iky.dtf.model.Entry
import su.iky.dtf.network.getFeed

class FeedView : ViewModel() {
    val liveEntries = MutableLiveData<List<Entry>>()

    fun getEntries() {
        val entries = liveEntries.value?.toMutableList() ?: mutableListOf<Entry>()
        CoroutineScope(Dispatchers.Main).launch {
            entries.addAll(getFeed())
            liveEntries.value=entries
        }
    }
}