package dev.skyfish.memory.memory_feature.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.skyfish.memory.memory_feature.presentation.memory_screen.MemoryScreen
import dev.skyfish.memory.memory_feature.presentation.memory_screen.MemoryViewModel
import dev.skyfish.memory.ui.theme.MemoryTheme
import android.media.MediaPlayer
import dev.skyfish.memory.R

class MainActivity : ComponentActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private val tracks = listOf(
        R.raw.track1,
        R.raw.track2,
        R.raw.track9,
        R.raw.track10,
        R.raw.background_music

    )
    private var currentTrackIndex by mutableStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: MemoryViewModel by viewModels()
                    MemoryScreen(viewModel = viewModel)
                    TrackSwitchButton()
                }
            }
        }

        mediaPlayer = MediaPlayer.create(this, tracks[currentTrackIndex])
        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }

    @Composable
    fun TrackSwitchButton() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = { switchTrack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier.size(30.dp)
            ) {}
        }
    }

    private fun switchTrack() {
        mediaPlayer.stop()
        currentTrackIndex = (currentTrackIndex + 1) % tracks.size
        mediaPlayer = MediaPlayer.create(this, tracks[currentTrackIndex])
        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
    }
}
