package dev.ferp.navigationsamples.data

interface PictureRepository {
    suspend fun getAllPictures(): Result<List<Picture>>
    suspend fun areDetailsAvailable(index: Int): Boolean
}