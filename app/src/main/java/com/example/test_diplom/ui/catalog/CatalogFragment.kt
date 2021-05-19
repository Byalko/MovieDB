package com.example.test_diplom.ui.catalog

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.test_diplom.databinding.FragmentCatalogBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CatalogFragment : Fragment() {

    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CatalogViewModel by activityViewModels()

    private var videoId: String = "www"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button2.setOnClickListener {
            //viewModel.getFilm(binding.idFilm.text.toString())
            //viewModel.getFilm("301")
            /*if (videoId.isNotEmpty()){
                val intent = Intent(this, YouTubeActivity::class.java).apply {
                    putExtra(EXTRA_MESSAGE, videoId)
                }
                startActivity(intent)
            }*/
        }

        lifecycleScope.launchWhenStarted {
            viewModel.film.collect { event ->
                when (event) {
                    is FilmEvent.Success -> {
                        binding.progressBar.isVisible = false
                        binding.nameRu.setTextColor(Color.BLACK)
                        binding.nameRu.text = event.resultText
                        videoId = "L0fw0WzFaBM"
                    }
                    is FilmEvent.Failure -> {
                        binding.progressBar.isVisible = false
                        binding.nameRu.setTextColor(Color.RED)
                        binding.nameRu.text = event.errorText

                    }
                    is FilmEvent.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    else -> Unit
                } }
        }
    }

    /*private fun play(youTubePlayerView: YouTubePlayerView) {
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                //youTubePlayer.loadVideo(videoId, 0f)
                youTubePlayer.loadOrCueVideo(lifecycle, videoId, 0f)
            }

            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                super.onError(youTubePlayer, error)
                Toast.makeText(this@MainActivity, videoId,Toast
                    .LENGTH_SHORT).show()
            }
        })
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}