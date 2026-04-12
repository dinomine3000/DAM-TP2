package dam_A51728.imageapiapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dam_A51728.imageapiapp.adapter.ImageAdapter
import dam_A51728.imageapiapp.data.AppDatabase
import dam_A51728.imageapiapp.databinding.ActivityFavoritesBinding
import dam_A51728.imageapiapp.network.NetworkModule
import dam_A51728.imageapiapp.repository.DogRepository
import dam_A51728.imageapiapp.viewmodel.DogViewModel
import dam_A51728.imageapiapp.viewmodel.DogViewModelFactory

/**
 * Activity to display the list of favorited images.
 */
class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var adapter: ImageAdapter

    private val viewModel: DogViewModel by viewModels {
        val database = AppDatabase.getDatabase(applicationContext)
        DogViewModelFactory(DogRepository(NetworkModule.dogApiService, database.favoritesDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setupRecyclerView()
        setupObservers()
        
        // Load favorite images logic will be implemented in Step 8
    }

    private fun setupRecyclerView() {
        adapter = ImageAdapter(
            onImageClick = { item ->
                val intent = Intent(this, ImageActivity::class.java).apply {
                    putExtra("EXTRA_IMAGE_URL", item.url)
                }
                startActivity(intent)
            },
            onFavoriteClick = { item ->
                viewModel.toggleFavorite(item)
            }
        )
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.favorites.observe(this) { favorites ->
            adapter.submitList(favorites)
            adapter.setFavorites(favorites.map { it.url }.toSet())
        }
    }
}
