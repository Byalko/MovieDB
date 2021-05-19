package com.example.test_diplom.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.test_diplom.databinding.DetailFragmentBinding
import kotlinx.coroutines.flow.collect

class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by activityViewModels()
    private val args: DetailFragmentArgs by navArgs()

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.id
        viewModel.getFilm(id)

        lifecycleScope.launchWhenStarted {
            viewModel.film.collect { film ->
                when (film) {
                    is FilmEvent.Success -> {
                        binding.poster.load("https://image.tmdb.org/t/p/w154" + film.result.poster_path) {
                            crossfade(true)
                            //placeholder(R.drawable.ic_placeholder)
                            //transformations(RoundedCornersTransformation())
                        }
                        binding.banner.load("https://image.tmdb.org/t/p/original" + film.result.backdrop_path) {
                            crossfade(true)
                            //transformations(RoundedCornersTransformation())
                        }
                        binding.title.text = film.result.title
                        binding.voteAverage.text = film.result.vote_average.toString()
                        binding.voteCount.text = film.result.vote_count.toString()
                        binding.releaseDate.text = film.result.release_date
                        binding.shortDescriptions.text = film.result.overview
                        binding.progress.visibility = View.GONE

                        with(binding) {
                            voteAverageLabel.visibility = View.VISIBLE
                            voteCountLabel.visibility = View.VISIBLE
                            releaseDateLabel.visibility = View.VISIBLE
                            actorsLabel.visibility = View.VISIBLE
                        }
                    }
                    is FilmEvent.Failure -> {
                        //binding.res.text = film.errorText
                    }
                    is FilmEvent.Empty -> {
                    }
                    is FilmEvent.Loading -> {
                        with(binding) {
                            progress.visibility = View.VISIBLE
                            voteAverageLabel.visibility = View.INVISIBLE
                            voteCountLabel.visibility = View.INVISIBLE
                            releaseDateLabel.visibility = View.INVISIBLE
                            actorsLabel.visibility = View.INVISIBLE
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}