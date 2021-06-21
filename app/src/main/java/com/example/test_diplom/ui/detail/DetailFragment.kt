package com.example.test_diplom.ui.detail

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.test_diplom.R
import com.example.test_diplom.data.model.db.toDetailFilmDB
import com.example.test_diplom.databinding.DetailFragmentBinding
import com.example.test_diplom.ui.home.adapter.HomeAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect

class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by activityViewModels()
    private val args: DetailFragmentArgs by navArgs()

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.id
        viewModel.getFilm(id)


        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnRetry.setOnClickListener {
            viewModel.getFilm(id)
        }

        viewModel.category.observe(viewLifecycleOwner, {
            adapter.differ.submitList(it)
            var mLastClickTime = 0L
            adapter.setOnItemClickListener {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return@setOnItemClickListener
                }
                mLastClickTime = SystemClock.elapsedRealtime()

                val bundle = bundleOf("id" to 200)

                findNavController().navigate(
                    R.id.action_homeFragment_to_detailFragment, bundle
                )
            }
        })

        lifecycleScope.launchWhenStarted {
            viewModel.film.collect { film ->
                when (film) {
                    is FilmEvent.Success -> {
                        binding.poster.load("https://image.tmdb.org/t/p/w154" + film.result.poster_path) {
                            crossfade(true)
                            //placeholder(R.drawable.ic_placeholder)
                            //transformations(RoundedCornersTransformation())
                        }
                        binding.banner.load("https://image.tmdb.org/t/p/w500" + film.result.backdrop_path) {
                            crossfade(true)
                            //transformations(BlurTransformation())
                        }
                        binding.title.text = film.result.title
                        binding.voteAverage.text = film.result.vote_average.toString()
                        binding.voteCount.text = film.result.vote_count.toString()
                        binding.releaseDate.text = film.result.release_date
                        binding.shortDescriptions.text = film.result.overview
                        binding.progress.visibility = View.GONE

                        binding.btnLike.setOnClickListener {
                            viewModel.saveMovie(film.result.toDetailFilmDB())
                            Snackbar.make(view,"Movie save successfully", Snackbar.LENGTH_SHORT).show()
                        }

                        with(binding) {
                            title.visibility = View.VISIBLE
                            voteAverageLabel.visibility = View.VISIBLE
                            voteCountLabel.visibility = View.VISIBLE
                            releaseDateLabel.visibility = View.VISIBLE
                            //similarLabel.visibility = View.VISIBLE
                            genreList.visibility = View.VISIBLE

                            errorImage.visibility = View.INVISIBLE
                            errorTxt.visibility = View.INVISIBLE
                            btnRetry.visibility = View.INVISIBLE
                        }
                    }
                    is FilmEvent.Failure -> {
                        binding.errorTxt.text = film.errorText
                        binding.errorImage.visibility = View.VISIBLE
                        binding.errorTxt.visibility = View.VISIBLE
                        binding.btnRetry.visibility = View.VISIBLE
                        binding.progress.visibility = View.INVISIBLE
                    }
                    is FilmEvent.Empty -> {
                    }
                    is FilmEvent.Loading -> {
                        with(binding) {
                            progress.visibility = View.VISIBLE
                            voteAverageLabel.visibility = View.INVISIBLE
                            voteCountLabel.visibility = View.INVISIBLE
                            releaseDateLabel.visibility = View.INVISIBLE
                            //similarLabel.visibility = View.INVISIBLE
                            genreList.visibility = View.INVISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = HomeAdapter()
        binding.genreList.adapter = adapter
        binding.genreList.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        /*adapter = ItemAdapter("detail")
        binding.similarList.adapter = adapter
        binding.similarList.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)*/
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}