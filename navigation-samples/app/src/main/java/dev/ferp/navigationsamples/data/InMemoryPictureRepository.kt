package dev.ferp.navigationsamples.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

class InMemoryPictureRepository @Inject constructor() : PictureRepository {

    override suspend fun getAllPictures(): Result<List<Picture>> {
        return Result.success(
            (1..10).map {
                Picture("https://picsum.photos/600/900")
            }
        )
    }

    override suspend fun areDetailsAvailable(picture: Picture): Boolean {
        return withContext(Dispatchers.IO) {
            delay(300) // Fake network delay
            Random.nextBoolean()
        }
    }
}