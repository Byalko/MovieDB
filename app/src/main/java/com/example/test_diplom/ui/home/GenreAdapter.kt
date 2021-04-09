package com.example.test_diplom.ui.home

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.test_diplom.R
import com.example.test_diplom.data.model.homeFragment.popular.ItemHome
import com.example.test_diplom.databinding.ListItemGenreBinding


class GenreAdapter : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    class GenreViewHolder(val binding: ListItemGenreBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<ItemHome>() {
        override fun areItemsTheSame(oldItem: ItemHome, newItem: ItemHome): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ItemHome, newItem: ItemHome): Boolean {
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
        var mLastClickTime = 0L

        val category = differ.currentList[position]
        holder.apply {
            binding.article.text = category.title
            itemView.setOnClickListener {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return@setOnClickListener
                }
                mLastClickTime = SystemClock.elapsedRealtime()
                it.findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
            }
        }
    }
}