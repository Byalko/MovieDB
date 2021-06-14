package com.example.test_diplom.ui.profile

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test_diplom.R
import com.example.test_diplom.databinding.FragmentProfileBinding
import com.example.test_diplom.ui.search.SearchAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchAdapter: SearchAdapter
    private val viewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var mLastClickTime = 0L
        searchAdapter.setOnItemClickListener {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return@setOnItemClickListener
            }
            mLastClickTime = SystemClock.elapsedRealtime()

            val bundle = bundleOf("id" to it)

            findNavController().navigate(
                R.id.action_profileFragment_to_detailFragment,bundle
            )
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val movie = searchAdapter.differ.currentList[position]
                viewModel.deleteMovie(movie)
                Snackbar.make(view,"Article delete successfully", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.saveMovie(movie)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply{
            attachToRecyclerView(binding.itemFilm)
        }

        viewModel.movies.observe(viewLifecycleOwner,{
            if (it.isNotEmpty()){
                searchAdapter.differ.submitList(it)
                binding.message.visibility = View.GONE
            } else {
                binding.message.visibility = View.VISIBLE
                binding.message.text = "No movies"
                searchAdapter.differ.submitList(emptyList())
            }
        })
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