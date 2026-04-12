package dam_A51728.imageapiapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Object to represent and store each image.
 *
 * @property url The URL from the API, serving as the Unique Resource Locator and ID.
 * @property favoriteOrder A value incremented globally each time an image is favorited, used for ordering.
 */
@Entity(tableName = "favorites")
data class ImageItem(
    @PrimaryKey val url: String,
    var favoriteOrder: Long = 0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ImageItem
        return url == other.url
    }

    override fun hashCode(): Int {
        return url.hashCode()
    }
}
