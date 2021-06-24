package com.example.test_diplom.ui.search

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test_diplom.R
import com.example.test_diplom.data.model.searchFragment.toDetailFilmDB
import com.example.test_diplom.databinding.FragmentSearchBinding
import com.example.test_diplom.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchAdapter: SearchAdapter
    private val viewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        var job: Job? = null
        binding.searchView.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.getSearchFilm(it.toString())
                    }
                }
            }
        }

        var mLastClickTime = 0L
        searchAdapter.setOnItemClickListener {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return@setOnItemClickListener
            }
            mLastClickTime = SystemClock.elapsedRealtime()

            val bundle = bundleOf("id" to it)

            findNavController().navigate(
                R.id.action_searchFragment_to_detailFragment,bundle
            )
        }

        viewModel.searchNews.observe(viewLifecycleOwner, { it ->
            when (it) {
                is Resource.Success -> {
                    hideProgressBar()
                    it.data?.let { movieList ->
                        searchAdapter.differ.submitList(movieList.results.
                        map { it.toDetailFilmDB() }.filter { it.poster_path!="null" })
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    it.message?.let {
                        Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        searchAdapter = SearchAdapter()
        binding.itemFilm.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}