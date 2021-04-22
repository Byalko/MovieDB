package com.example.test_diplom.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.test_diplom.databinding.DetailFragmentBinding
import kotlinx.coroutines.flow.collect

class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by activityViewModels()
    private val args : DetailFragmentArgs by navArgs()

    private var _binding : DetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.id
        viewModel.getFilm(id)

        lifecycleScope.launchWhenStarted {
            viewModel.film.collect { film ->
                when(film){
                    is FilmEvent.Success -> {
                        binding.res.text = film.result.title
                        binding.progress.visibility = View.GONE
                    }
                    is FilmEvent.Failure -> {
                        binding.res.text = film.errorText
                    }
                    is FilmEvent.Empty -> {
                        binding.res.text = "No data"
                    }
                    is FilmEvent.Loading -> {
                        binding.progress.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}