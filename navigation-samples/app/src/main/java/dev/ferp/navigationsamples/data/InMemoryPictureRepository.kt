package dev.ferp.navigationsamples.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class InMemoryPictureRepository @Inject constructor() : PictureRepository {

    override suspend fun getPictureById(pictureId: String): Result<Picture> {
        return allPictures.find { it.id == pictureId }
            ?.let { Result.success(it) }
            ?: Result.failure(IOException())
    }

    override suspend fun getAllPictures(): Result<List<Picture>> {
        return Result.success(allPictures)
    }

    override suspend fun areDetailsAvailable(index: Int): Boolean {
        return withContext(Dispatchers.IO) {
            delay(1_000) // Fake network delay
            true//index < 5
        }
    }

    companion object {
        private const val ITEM_COUNT = 10

        // Let's not overcomplicate this example with separate data sources
        private val allPictures by lazy {
            (1..ITEM_COUNT).mapNotNull { index ->
                val baseUrl = "https://picsum.photos/id/${index * 19}"
                Picture(
                    id = baseUrl,
                    url = "$baseUrl/600/900",
                    thumbnailUrl = "$baseUrl/200/300"
                )
            }
        }
    }
}