package dam_A51728.imageapiapp.network

import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiService {
    @GET("breeds/image/random/{count}")
    suspend fun getRandomImages(@Path("count") count: Int): DogImageResponse

    @GET("breed/{breed}/images/random/{count}")
    suspend fun getImagesByBreed(
        @Path("breed") breed: String,
        @Path("count") count: Int
    ): DogImageResponse

    @GET("breeds/list/all")
    suspend fun getBreeds(): DogBreedResponse
}

data class DogImageResponse(
    val message: List<String>,
    val status: String
)

data class DogBreedResponse(
    val message: Map<String, List<String>>,
    val status: String
)
