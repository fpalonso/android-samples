package dev.ferp.navigationsamples.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import dev.ferp.navigationsamples.R
import dev.ferp.navigationsamples.ui.theme.NavigationSamplesTheme

@Composable
fun DetailsScreen(
    pictureId: String,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel(),
    onClose: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadPicture(pictureId)
    }

    DetailsScreen(
        modifier = modifier,
        pictureUrl = uiState.pictureUrl.orEmpty(),
        placeholderCacheKey = uiState.placeholderCacheKey.orEmpty(),
        onClose = onClose
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    pictureUrl: String,
    placeholderCacheKey: String,
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {}
) {
    Scaffold(
        modifier
            .background(Color.Black)
            .fillMaxSize()
            .clickable { onClose() },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = Color.Transparent,
                    navigationIconContentColor = Color.White
                ),
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = onClose
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        val imageModifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .padding(contentPadding)
        if (LocalInspectionMode.current) {
            Image(
                modifier = imageModifier,
                painter = painterResource(R.drawable.cute_dog),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        } else {
            val context = LocalContext.current
            AsyncImage(
                modifier = imageModifier,
                model = ImageRequest.Builder(context)
                    .data(pictureUrl)
                    .placeholderMemoryCacheKey(placeholderCacheKey)
                    .build(),
                contentDescription = null
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun DetailsScreenPreview() {
    NavigationSamplesTheme {
        DetailsScreen(
            pictureId = ""
        )
    }
}