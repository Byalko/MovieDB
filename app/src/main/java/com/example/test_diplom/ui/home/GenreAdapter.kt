package com.example.test_diplom.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.test_diplom.data.model.genre.GenreX
import com.example.test_diplom.databinding.ListItemGenreBinding


class GenreAdapter : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    class GenreViewHolder(val binding: ListItemGenreBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<GenreX>() {
        override fun areItemsTheSame(oldItem: GenreX, newItem: GenreX): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GenreX, newItem: GenreX): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = ListItemGenreBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return GenreViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = differ.currentList[position]
        with(holder) {
            binding.article.text = genre.name
        }
        /*holder.itemView.apply {
            val article: TextView = findViewById(R.id.article)
            article.text = genre.name
            setOnClickListener {
                onItemClickListener?.let {
                    it(genre)
                }
            }
        }*/
    }

    private var onItemClickListener: ((GenreX) -> Unit)? = null

    fun setOnItemClickListener(listener: (GenreX) -> Unit) {
        onItemClickListener = listener
    }
}
