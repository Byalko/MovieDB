package com.example.test_diplom.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test_diplom.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collect


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        //viewModel.getGenres()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipe.setOnRefreshListener {
            viewModel.getGenres()
            //binding.swipe.isRefreshing = false
        }

        lifecycleScope.launchWhenStarted {

            viewModel.genr.collect {
                when (it) {
                    is GenreEvent.Success -> {
                        val adapter = GenreAdapter()
                        binding.genreList.adapter = adapter
                        binding.genreList.layoutManager = LinearLayoutManager(activity)
                        adapter.differ.submitList(it.resultText.genres)
                        //binding.progress.visibility = View.GONE
                        binding.swipe.isRefreshing = false
                    }
                    is GenreEvent.Failure -> {
                        with(binding) {
                            genreList.visibility = View.GONE
                            //progress.visibility = View.GONE
                            errorImage.visibility = View.VISIBLE
                            errorTxt.visibility = View.VISIBLE
                            errorTxt.text = it.errorText
                            binding.swipe.isRefreshing = false
                        }
                    }
                    is GenreEvent.Empty -> binding.genreList.visibility = View.GONE
                    is GenreEvent.Loading -> {
                        //binding.progress.visibility = View.VISIBLE
                        binding.swipe.isRefreshing = true
                        binding.errorImage.visibility = View.GONE
                        binding.errorTxt.visibility = View.GONE
                        binding.errorTxt.visibility = View.GONE

                    }
                    }
                }
            }
        }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
