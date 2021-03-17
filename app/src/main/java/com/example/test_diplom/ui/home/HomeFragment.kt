package com.example.test_diplom.ui.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test_diplom.R
import com.example.test_diplom.databinding.FragmentHomeBinding
import com.google.android.youtube.player.internal.v
import kotlinx.coroutines.flow.collect


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by activityViewModels()

    private lateinit var adapter : GenreAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupRecyclerView()

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
                        adapter.differ.submitList(it.resultText.genres)
                        adapter.setOnItemClickListener {
                            Log.i("adapter","click $it")
                            val bundle = Bundle().apply{
                                putParcelable("genreFromCatalog",it)
                            }
                            findNavController().navigate(
                                R.id.action_homeFragment_to_detailFragment,
                                bundle
                            )
                        }
                        binding.swipe.isRefreshing = false
                    }
                    is GenreEvent.Failure -> {
                        with(binding) {
                            errorImage.visibility = View.VISIBLE
                            errorTxt.visibility = View.VISIBLE
                            errorTxt.text = it.errorText
                            errorTxt.setTextColor(Color.RED)
                            binding.swipe.isRefreshing = false
                        }
                        adapter.differ.submitList(emptyList())
                    }
                    is GenreEvent.Empty -> { }
                    is GenreEvent.Loading -> {
                        with(binding){
                            swipe.isRefreshing = true
                            errorImage.visibility = View.GONE
                            errorTxt.visibility = View.GONE
                            errorTxt.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView(){
        adapter = GenreAdapter()
        binding.genreList.adapter = adapter
        binding.genreList.layoutManager = GridLayoutManager(activity,2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
