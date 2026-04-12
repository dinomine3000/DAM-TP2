package dam_A51728.imageapiapp.model

/**
 * Object to store all the favorited images.
 *
 * @property favoriteImageUrls List of image URLs (IDs) favorited by the user.
 * Sorted by Favorite Order attribute, with the most recent (biggest number) first.
 */
data class FavoriteList(
    val favoriteImageUrls: List<String> = emptyList()
)
