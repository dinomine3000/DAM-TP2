package dam_A51728.imageapiapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dam_A51728.imageapiapp.model.ImageItem
import dam_A51728.imageapiapp.repository.DogRepository
import kotlinx.coroutines.launch

class DogViewModel(private val repository: DogRepository) : ViewModel() {

    val favorites: LiveData<List<ImageItem>> = repository.allFavorites.asLiveData()

    private val _images = MutableLiveData<List<ImageItem>>()
    val images: LiveData<List<ImageItem>> = _images

    private val _breeds = MutableLiveData<List<String>>()
    val breeds: LiveData<List<String>> = _breeds

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadRandomImages(count: Int = 10) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val list = repository.getRandomImages(count)
                _images.value = list
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load images"
            } finally {
                _loading.value = false
            }
        }
    }

    fun loadImagesByBreed(breed: String, count: Int = 10) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val list = repository.getImagesByBreed(breed, count)
                _images.value = list
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load breed images"
            } finally {
                _loading.value = false
            }
        }
    }

    fun loadBreeds() {
        viewModelScope.launch {
            try {
                _breeds.value = repository.getBreeds()
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load breeds"
            }
        }
    }

    fun toggleFavorite(image: ImageItem) {
        viewModelScope.launch {
            repository.toggleFavorite(image)
        }
    }
}
