package dev.ferp.navigationsamples.data

interface PictureRepository {
    suspend fun getPictureById(pictureId: String): Result<Picture>
    suspend fun getAllPictures(): Result<List<Picture>>
    suspend fun areDetailsAvailable(index: Int): Boolean
}