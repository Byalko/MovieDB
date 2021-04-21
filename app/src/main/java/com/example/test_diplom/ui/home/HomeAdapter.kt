package com.example.test_diplom.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test_diplom.data.model.homeFragment.ItemHome
import com.example.test_diplom.databinding.ListIemCategoryBinding

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    class HomeViewHolder(val binding: ListIemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<ItemHome>() {
        override fun areItemsTheSame(oldItem: ItemHome, newItem: ItemHome): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: ItemHome, newItem: ItemHome): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ListIemCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val category = differ.currentList[position]
        holder.apply {
            binding.textView.text = category.name
            val adapter = GenreAdapter()
            binding.listFilm.adapter = adapter
            binding.listFilm.layoutManager =
                LinearLayoutManager(binding.listFilm.context, LinearLayoutManager.HORIZONTAL, false)
            adapter.differ.submitList(category.content.results)

            binding.viewMore.setOnClickListener {
                onItemClickListener?.let {
                    it(category)
                }
            }
        }
    }

    private var onItemClickListener: ((ItemHome) -> Unit)? = null

    fun setOnItemClickListener(listener: (ItemHome) -> Unit) {
        onItemClickListener = listener
    }
}