package dam_A51728.imageapiapp.data

import androidx.room.*
import dam_A51728.imageapiapp.model.ImageItem
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites ORDER BY favoriteOrder DESC")
    fun getAllFavorites(): Flow<List<ImageItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: ImageItem)

    @Delete
    fun delete(image: ImageItem)

    @Query("SELECT MAX(favoriteOrder) FROM favorites")
    fun getMaxOrder(): Long?

    @Query("SELECT EXISTS(SELECT * FROM favorites WHERE url = :url)")
    fun isFavorite(url: String): Boolean
}

@Database(entities = [ImageItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: android.content.Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "dog_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
