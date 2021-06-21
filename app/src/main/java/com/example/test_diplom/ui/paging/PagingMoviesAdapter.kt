package com.example.test_diplom.ui.paging

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.test_diplom.R
import com.example.test_diplom.data.model.homeFragment.popular.ItemFilm
import com.example.test_diplom.databinding.ItemMoviesBinding
import com.example.test_diplom.util.Constans

class PagingMoviesAdapter() :
    PagingDataAdapter<ItemFilm, MovieViewHolder>(ArticleDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMoviesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class MovieViewHolder(val binding: ItemMoviesBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(article: ItemFilm?) {
        with(binding) {
            image.load(Constans.DEFAULT_IMAGE_URL+article?.poster_path) {
                placeholder(ColorDrawable(Color.TRANSPARENT))
            }
        }

        var mLastClickTime = 0L
        itemView.setOnClickListener {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return@setOnClickListener
            }
            mLastClickTime = SystemClock.elapsedRealtime()
            val bundle = bundleOf("id" to (article?.id ?: 200))
            it.findNavController().navigate(R.id.action_pagingFragment_to_detailFragment, bundle)
        }
    }
}

private object ArticleDiffItemCallback : DiffUtil.ItemCallback<ItemFilm>() {

    override fun areItemsTheSame(oldItem: ItemFilm, newItem: ItemFilm): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ItemFilm, newItem: ItemFilm): Boolean {
        return oldItem.title == newItem.title && oldItem.backdropPath == newItem.backdropPath
    }
}