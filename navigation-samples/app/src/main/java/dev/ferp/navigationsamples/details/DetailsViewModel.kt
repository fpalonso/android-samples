package dev.ferp.navigationsamples.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ferp.navigationsamples.data.PictureRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailsUiState(
    val placeholderCacheKey: String? = null,
    val pictureUrl: String? = null,
)

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val pictureRepository: PictureRepository
) : ViewModel() {

    val uiState: StateFlow<DetailsUiState>
        field = MutableStateFlow(DetailsUiState())

    fun loadPicture(pictureId: String) {
        viewModelScope.launch {
            pictureRepository.getPictureById(pictureId).fold(
                onSuccess = { picture ->
                    uiState.update { currentState ->
                        currentState.copy(
                            placeholderCacheKey = picture.id,
                            pictureUrl = picture.url
                        )
                    }
                },
                onFailure = {} // TODO
            )
        }
    }
}