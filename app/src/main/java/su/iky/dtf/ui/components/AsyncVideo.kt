package su.iky.dtf.ui.components

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player.REPEAT_MODE_ALL
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.media3.ui.PlayerView.SHOW_BUFFERING_NEVER
import su.iky.dtf.lib.network.pxToDp


@SuppressLint("OpaqueUnitKey", "UnsafeOptInUsageError")
@Composable
fun AsyncVideo(model: String, preview: String, imageRatio: Double, modifier: Modifier) {
    var size by remember { mutableStateOf(IntSize.Zero) }

    val context = LocalContext.current

    val mediaItem = MediaItem.Builder()
        .setUri(model)
        .setMediaMetadata(MediaMetadata.Builder().setArtworkUri(Uri.parse(preview)).build())
        .build()

    val exoPlayer = remember(context, mediaItem) {
        ExoPlayer.Builder(context)
            .build()
            .also {
                exoPlayer ->
                    exoPlayer.setMediaItem(mediaItem)
                    exoPlayer.prepare()
                    exoPlayer.playWhenReady = false
                    exoPlayer.repeatMode = REPEAT_MODE_ALL
            }
    }

    return DisposableEffect(
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged {
                    size = it
                }
                .then(modifier)
        ) {
            val h = (size.width * imageRatio).toInt().pxToDp()
            AndroidView(factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = true
                    setShowNextButton(false)
                    setShowPreviousButton(false)
                    setShowFastForwardButton(false)
                    setShowRewindButton(false)
                    setShowMultiWindowTimeBar(false)
                    setShowBuffering(SHOW_BUFFERING_NEVER)
                    setShowVrButton(false)
                    setShowSubtitleButton(false)
                    setShowShuffleButton(false)
                }
            }, modifier = Modifier.fillMaxWidth().height(h))
        }
    ) {
        onDispose { exoPlayer.release() }
    }

}