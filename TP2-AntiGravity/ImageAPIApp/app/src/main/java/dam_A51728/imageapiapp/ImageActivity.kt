package dam_A51728.imageapiapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import dam_A51728.imageapiapp.R
import dam_A51728.imageapiapp.databinding.ActivityImageDetailBinding

/**
 * Activity to display a single image in focus.
 */
class ImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val imageUrl = intent.getStringExtra("EXTRA_IMAGE_URL")
        imageUrl?.let {
            binding.fullImageView.load(it) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
            }
        }
    }
}
