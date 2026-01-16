package dev.ferp.navigationsamples.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import dev.ferp.navigationsamples.R
import dev.ferp.navigationsamples.ui.theme.NavigationSamplesTheme

@Composable
fun GalleryScreen(
    modifier: Modifier = Modifier,
    viewModel: GalleryViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    GalleryScreen(
        modifier = modifier,
        state = state,
        onItemClick = viewModel::onItemClick
    )
}

@Composable
private fun GalleryScreen(
    state: GalleryUiState,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit = {}
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        columns = GridCells.Adaptive(minSize = 160.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(items = state.items) { index, item ->
            GalleryItem(
                pictureUrl = item.pictureUrl,
                text = stringResource(R.string.picture_x, index + 1),
                isLoading = state.loadingIndex == index,
                onClick = { onItemClick(index) }
            )
        }
    }
}

@Composable
private fun GalleryItem(
    pictureUrl: String,
    text: String,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.size(160.dp, 300.dp),
        onClick = onClick
    ) {
        val imageModifier = Modifier.weight(1f)
        val imageContentScale = ContentScale.Crop
        if (LocalInspectionMode.current) {
            Image(
                modifier = imageModifier,
                painter = painterResource(R.drawable.cute_dog),
                contentDescription = null,
                contentScale = imageContentScale
            )
        } else {
            AsyncImage(
                modifier = imageModifier,
                model = pictureUrl,
                contentDescription = null,
                contentScale = imageContentScale
            )
        }
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = text
            )
            if (isLoading) {
                CircularProgressIndicator(Modifier.size(24.dp))
            }
        }
    }
}

@Preview
@Composable
private fun GalleryItemPreview() {
    GalleryItem(
        pictureUrl = "",
        text = "Cute dog",
        isLoading = true
    )
}

@PreviewLightDark
@Composable
private fun GalleryPreview() {
    NavigationSamplesTheme {
        Scaffold { contentPadding ->
            GalleryScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                state = GalleryUiState(
                    items = (1..10).map {
                        GalleryItemModel(pictureUrl = "")
                    }
                )
            )
        }
    }
}