package dev.ferp.navigationsamples.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ferp.navigationsamples.data.PictureRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GalleryUiState(
    val items: List<GalleryItemModel> = emptyList(),
    val loadingIndex: Int? = null
)

enum class GalleryError {
    ERROR_LOADING_GALLERY
}

sealed interface GalleryEvent {
    data class NavigationToDetails(val pictureId: String) : GalleryEvent
    data class DetailsUnavailable(val index: Int) : GalleryEvent
    data class Error(val cause: GalleryError) : GalleryEvent
}

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val pictureRepository: PictureRepository
) : ViewModel() {

    val uiState: StateFlow<GalleryUiState>
        field = MutableStateFlow(GalleryUiState())

    val events: SharedFlow<GalleryEvent>
        field = MutableSharedFlow()

    private var loadingIndex: Int? = null
        set(value) = uiState.update { currentState ->
            currentState.copy(
                loadingIndex = value
            )
        }

    private var processItemJob: Job? = null

    init {
        loadGallery()
    }

    internal fun loadGallery() {
        viewModelScope.launch {
            pictureRepository.getAllPictures().fold(
                onSuccess = { pictures ->
                    uiState.update {
                        GalleryUiState(
                            items = pictures.toGalleryItemModelList()
                        )
                    }
                },
                onFailure = {
                    events.emit(GalleryEvent.Error(GalleryError.ERROR_LOADING_GALLERY))
                }
            )
        }
    }

    internal fun onItemClick(index: Int) {
        processItemJob?.cancel()
        processItemJob = viewModelScope.launch {
            loadingIndex = index
            if (pictureRepository.areDetailsAvailable(index)) {
                val pictureId = uiState.value.items[index].pictureId
                events.emit(GalleryEvent.NavigationToDetails(pictureId))
            } else {
                events.emit(GalleryEvent.DetailsUnavailable(index))
            }
            loadingIndex = null
        }
    }
}