package com.example.test_diplom.ui.home.adapter

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.test_diplom.R
import com.example.test_diplom.data.model.homeFragment.popular.ItemFilm
import com.example.test_diplom.databinding.ListItemFilmBinding


class ItemAdapter(val destination: String?) : RecyclerView.Adapter<ItemAdapter.GenreViewHolder>() {

    class GenreViewHolder(val binding: ListItemFilmBinding) : RecyclerView.ViewHolder(binding.root)

    private val defaultUrl = "https://image.tmdb.org/t/p/w200"

    private val differCallback = object : DiffUtil.ItemCallback<ItemFilm>() {
        override fun areItemsTheSame(oldItem: ItemFilm, newItem: ItemFilm): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ItemFilm, newItem: ItemFilm): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = ListItemFilmBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return GenreViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        var mLastClickTime = 0L

        val film = differ.currentList[position]
        holder.apply {
            val uri = film.poster_path
            binding.poster.load(defaultUrl + uri) {
                crossfade(true)
                //placeholder(R.drawable.ic_placeholder)
                transformations(RoundedCornersTransformation())
            }
            itemView.setOnClickListener {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return@setOnClickListener
                }
                mLastClickTime = SystemClock.elapsedRealtime()
                destination?.let { dest->
                    val bundle = bundleOf("id" to film.id)
                    when(dest){
                        "home" -> {
                            it.findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
                        }
                        "detail" -> {
                            it.findNavController().navigate(R.id.action_detailFragment_self, bundle)
                        }
                    }
                }

            }
        }
    }
}