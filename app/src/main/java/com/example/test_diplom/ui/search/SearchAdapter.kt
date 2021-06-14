package com.example.test_diplom.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.test_diplom.data.model.homeFragment.popular.ItemFilm
import com.example.test_diplom.data.model.searchFragment.SearchItem
import com.example.test_diplom.databinding.ItemFilmPreviewBinding
import com.example.test_diplom.util.Constans.Companion.DEFAULT_IMAGE_URL

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(val binding: ItemFilmPreviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<SearchItem>() {
        override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding =
            ItemFilmPreviewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val film = differ.currentList[position]
        holder.apply {
            with(binding) {
                filmImage.load(DEFAULT_IMAGE_URL + film.poster_path)
                voteAverage.text = film.vote_average.toString()
                voteCount.text = film.vote_count.toString()
                title.text = film.title
                description.text = film.overview
                //publishedAt.text = film.popularity.toString()
            }
            itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(film.id)
                }
            }
        }
    }

    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }
}