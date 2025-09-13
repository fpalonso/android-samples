package dev.ferp.samples.imagepicker.addprofilepic

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import dev.ferp.samples.imagepicker.R

@Composable
fun AddProfilePicScreen(
    modifier: Modifier = Modifier,
    viewModel: AddProfilePicViewModel = hiltViewModel(),
    onSave: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val imagePicker = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { imageUri ->
        viewModel.onPictureSelected(imageUri?.toString())
    }

    AddProfilePicScreen(
        state = uiState,
        modifier = modifier,
        pickImage = {
            imagePicker.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        },
        clearImage = viewModel::clearPicture,
        onSaveClick = {
            viewModel.onSaveClick()
            onSave()
        },
        onDismiss = {
            viewModel.onDismiss()
            onDismiss()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddProfilePicScreen(
    state: AddProfilePicUiState,
    modifier: Modifier = Modifier,
    pickImage: () -> Unit = {},
    clearImage: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = stringResource(R.string.dismiss)
                        )
                    }
                },
                title = {
                    Text(stringResource(R.string.add_profile_picture))
                },
                actions = {
                    Button(onClick = onSaveClick) {
                        Text(stringResource(R.string.save))
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(256.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .clickable(onClick = pickImage),
                model = state.pictureUri.orEmpty(),
                contentDescription = stringResource(R.string.profile_picture),
                contentScale = ContentScale.Crop
            )
            if (state.pictureUri == null) {
                TextButton(onClick = pickImage) {
                    Text(stringResource(R.string.add_picture))
                }
            } else {
                Row {
                    TextButton(onClick = pickImage) {
                        Text(stringResource(R.string.edit_picture))
                    }
                    TextButton(onClick = clearImage) {
                        Text(stringResource(R.string.clear_picture))
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun AddProfilePicScreenPreview() {
    AddProfilePicScreen(
        state = AddProfilePicUiState(
            pictureUri = null
        )
    )
}

@Preview(showSystemUi = true)
@Composable
private fun EditOrClearProfilePicScreenPreview() {
    AddProfilePicScreen(
        state = AddProfilePicUiState(
            pictureUri = "content://pictures/1"
        )
    )
}