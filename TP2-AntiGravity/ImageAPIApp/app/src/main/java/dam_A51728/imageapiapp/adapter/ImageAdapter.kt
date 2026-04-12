package dam_A51728.imageapiapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import dam_A51728.imageapiapp.R
import dam_A51728.imageapiapp.databinding.ItemImageBinding
import dam_A51728.imageapiapp.model.ImageItem

class ImageAdapter(
    private val onImageClick: (ImageItem) -> Unit,
    private val onFavoriteClick: (ImageItem) -> Unit
) : ListAdapter<ImageItem, ImageAdapter.ViewHolder>(ImageDiffCallback()) {

    private var favoriteUrls: Set<String> = emptySet()

    fun setFavorites(urls: Set<String>) {
        favoriteUrls = urls
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, favoriteUrls.contains(item.url))
    }

    inner class ViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ImageItem, isFavorite: Boolean) {
            binding.imageView.load(item.url) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
                error(R.drawable.ic_launcher_background)
            }

            binding.root.setOnClickListener { onImageClick(item) }
            binding.btnFavorite.setOnClickListener { onFavoriteClick(item) }

            val heartTint = if (isFavorite) {
                android.R.color.holo_red_light
            } else {
                android.R.color.white
            }
            binding.btnFavorite.setColorFilter(binding.root.context.getColor(heartTint))
        }
    }

    class ImageDiffCallback : DiffUtil.ItemCallback<ImageItem>() {
        override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem == newItem
        }
    }
}
