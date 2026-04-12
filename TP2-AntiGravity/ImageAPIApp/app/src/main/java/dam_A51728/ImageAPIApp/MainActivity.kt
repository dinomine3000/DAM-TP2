package dam_A51728.imageapiapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dam_A51728.imageapiapp.adapter.ImageAdapter
import dam_A51728.imageapiapp.data.AppDatabase
import dam_A51728.imageapiapp.databinding.ActivityFeedBinding
import dam_A51728.imageapiapp.network.NetworkModule
import dam_A51728.imageapiapp.repository.DogRepository
import dam_A51728.imageapiapp.viewmodel.DogViewModel
import dam_A51728.imageapiapp.viewmodel.DogViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedBinding
    private lateinit var adapter: ImageAdapter
    
    private val viewModel: DogViewModel by viewModels {
        val database = AppDatabase.getDatabase(applicationContext)
        DogViewModelFactory(DogRepository(NetworkModule.dogApiService, database.favoritesDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        
        setupRecyclerView()
        setupObservers()
        setupBreedSelector()

        binding.fabRefresh.setOnClickListener {
            val selectedBreed = binding.breedSelector.text.toString()
            if (selectedBreed == "Random" || selectedBreed.isEmpty()) {
                viewModel.loadRandomImages()
            } else {
                viewModel.loadImagesByBreed(selectedBreed)
            }
        }

        // Initial load
        viewModel.loadRandomImages()
        viewModel.loadBreeds()
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

    private fun setupBreedSelector() {
        viewModel.breeds.observe(this) { breeds ->
            val list = mutableListOf("Random")
            list.addAll(breeds)
            val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, list)
            binding.breedSelector.setAdapter(adapter)
            binding.breedSelector.setText("Random", false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_feed, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorites -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupObservers() {
        viewModel.images.observe(this) { images ->
            adapter.submitList(images)
        }

        viewModel.favorites.observe(this) { favorites ->
            adapter.setFavorites(favorites.map { it.url }.toSet())
        }
    }
}
