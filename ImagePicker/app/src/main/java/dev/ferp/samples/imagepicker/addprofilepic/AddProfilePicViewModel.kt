package dev.ferp.samples.imagepicker.addprofilepic

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

internal data class AddProfilePicUiState(
    val pictureUri: String? = null
)

@HiltViewModel
class AddProfilePicViewModel @Inject constructor() : ViewModel() {

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

    private fun logPicturePicked() {
        Firebase.analytics.logEvent("profile_picture_picked", null)
    }

    private fun logPicturePickCancelled() {
        Firebase.analytics.logEvent("profile_picture_pick_cancelled", null)
    }

    private fun logPictureCleared() {
        Firebase.analytics.logEvent("profile_picture_cleared", null)
    }

    private fun logSaveClick() {
        Firebase.analytics.logEvent("profile_picture_save_click", null)
    }

    private fun logDismiss() {
        Firebase.analytics.logEvent("profile_picture_dismiss", null)
    }
}