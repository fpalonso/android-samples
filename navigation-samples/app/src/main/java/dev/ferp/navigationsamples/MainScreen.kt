package dev.ferp.navigationsamples

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.ferp.navigationsamples.details.DetailsScreen
import dev.ferp.navigationsamples.gallery.GalleryScreen

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var selectedPictureId by rememberSaveable { mutableStateOf<String?>(null) }
    AnimatedContent(
        modifier = modifier.fillMaxSize(),
        targetState = selectedPictureId != null,
        label = "show_details"
    ) { targetState ->
        if (!targetState) {
            GalleryScreen(
                showDetails = { pictureId ->
                    selectedPictureId = pictureId
                }
            )
        } else {
            selectedPictureId?.let { pictureId ->
                DetailsScreen(
                    pictureId = pictureId,
                    onClose = { selectedPictureId = null }
                )
            }
        }
    }
}