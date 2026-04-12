package dam_A51728.imageapiapp.repository

import dam_A51728.imageapiapp.data.FavoritesDao
import dam_A51728.imageapiapp.model.ImageItem
import dam_A51728.imageapiapp.network.DogApiService
import dam_A51728.imageapiapp.network.DogBreedResponse
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Data Repo to store and fetch all the relevant data from the Dog API and local Database.
 */
class DogRepository(
    private val apiService: DogApiService,
    private val favoritesDao: FavoritesDao
) {

    val allFavorites: Flow<List<ImageItem>> = favoritesDao.getAllFavorites()

    suspend fun toggleFavorite(image: ImageItem) {
        withContext(Dispatchers.IO) {
            if (favoritesDao.isFavorite(image.url)) {
                favoritesDao.delete(image)
            } else {
                val maxOrder = favoritesDao.getMaxOrder() ?: 0L
                image.favoriteOrder = maxOrder + 1
                favoritesDao.insert(image)
            }
        }
    }

    suspend fun isFavorite(url: String): Boolean {
        return withContext(Dispatchers.IO) {
            favoritesDao.isFavorite(url)
        }
    }

    /**
     * Fetches random image URLs and maps them to ImageItem objects.
     */
    suspend fun getRandomImages(count: Int): List<ImageItem> {
        val response = apiService.getRandomImages(count)
        return response.message.map { url -> ImageItem(url) }
    }

    /**
     * Fetches image URLs for a specific breed and maps them to ImageItem objects.
     */
    suspend fun getImagesByBreed(breed: String, count: Int): List<ImageItem> {
        val response = apiService.getImagesByBreed(breed, count)
        return response.message.map { url -> ImageItem(url) }
    }

    /**
     * Fetches the complete list of dog breeds.
     */
    suspend fun getBreeds(): List<String> {
        val response = apiService.getBreeds()
        // We only need the keys (breed names) as per requirements
        return response.message.keys.toList().sorted()
    }
}
