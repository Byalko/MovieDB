package com.example.test_diplom.ui.paging

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.addRepeatingJob
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.map
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.test_diplom.databinding.FragmentPagingBinding
import com.example.test_diplom.databinding.FragmentProfileBinding
import com.example.test_diplom.ui.detail.DetailFragmentArgs
import com.example.test_diplom.ui.search.SearchAdapter
import kotlinx.coroutines.flow.collectLatest

class PagingFragment : Fragment() {
    private val viewModel by activityViewModels<PagingViewModel>()

    private var _binding: FragmentPagingBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PagingMoviesAdapter
    private val args: PagingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPagingBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val destination = args.destination

        viewModel.toDestination(destination)

        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle,it)
        }
    }

    private fun setupRecyclerView() {
        adapter = PagingMoviesAdapter()
        binding.apply {
            movies.setHasFixedSize(true)
            movies.adapter = adapter
            movies.layoutManager = GridLayoutManager(activity,3)
        }
    }

}