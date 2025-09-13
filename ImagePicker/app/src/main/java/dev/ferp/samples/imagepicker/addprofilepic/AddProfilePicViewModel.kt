package dev.ferp.samples.imagepicker.addprofilepic

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ferp.samples.imagepicker.core.analytics.AddProfilePicLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

internal data class AddProfilePicUiState(
    val pictureUri: String? = null
)

@HiltViewModel
class AddProfilePicViewModel @Inject constructor(
    private val logger: AddProfilePicLogger
) : ViewModel(), AddProfilePicLogger by logger {

    private val _uiState = MutableStateFlow(AddProfilePicUiState())
    internal val uiState = _uiState.asStateFlow()

    internal fun onPictureSelected(uri: String?) {
        _uiState.update { currentState ->
            currentState.copy(pictureUri = uri)
        }
        if (uri != null) {
            logPicturePicked()
        } else {
            logPicturePickCancelled()
        }
    }

    internal fun clearPicture() {
        _uiState.update { currentState ->
            currentState.copy(pictureUri = null)
        }
        logPictureCleared()
    }

    internal fun onSaveClick() {
        logSaveClick()
    }

    internal fun onDismiss() {
        logDismiss()
    }
}