package dev.ferp.navigationsamples.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ferp.navigationsamples.data.PictureRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GalleryUiState(
    val items: List<GalleryItemModel> = emptyList()
)

enum class GalleryError {
    ERROR_LOADING_IMAGES
}

sealed interface GalleryEvent {
    data class NavigationToDetails(val index: Int) : GalleryEvent
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
                    events.emit(GalleryEvent.Error(GalleryError.ERROR_LOADING_IMAGES))
                }
            )
        }
    }

    internal fun onItemClick(index: Int) {
        viewModelScope.launch {
            setItemLoadingState(index, true)
            if (pictureRepository.areDetailsAvailable(index)) {
                events.emit(GalleryEvent.NavigationToDetails(index))
            } else {
                events.emit(GalleryEvent.DetailsUnavailable(index))
            }
            setItemLoadingState(index, false)
        }
    }

    private fun setItemLoadingState(index: Int, isLoading: Boolean) {
        uiState.update { currentState ->
            val newItems = currentState.items.mapIndexed { itemIndex, item ->
                if (itemIndex == index) {
                    item.copy(isLoading = isLoading)
                } else {
                    item
                }
            }
            currentState.copy(items = newItems)
        }
    }
}