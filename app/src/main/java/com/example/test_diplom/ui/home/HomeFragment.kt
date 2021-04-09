package com.example.test_diplom.ui.home

import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test_diplom.R
import com.example.test_diplom.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collect


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by activityViewModels()

    private lateinit var adapter : HomeAdapter

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
            viewModel.getAll()
            //viewModel.getGenres()
            //binding.swipe.isRefreshing = false
        }

        lifecycleScope.launchWhenStarted {
            viewModel.all.collect { all ->
                when(all){
                    is AllEvent.Success -> {
                        adapter.differ.submitList(all.resultText)
                        binding.swipe.isRefreshing = false
                        var mLastClickTime = 0L
                        adapter.setOnItemClickListener {
                            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                                return@setOnItemClickListener
                            }
                            mLastClickTime = SystemClock.elapsedRealtime()
                            val bundle = bundleOf("id" to 200)
                            Log.i("adapter1", "click $it")
                            /*val bundle = Bundle().apply{
                                putParcelable("genreFromCatalog",it)
                            }*/
                            findNavController().navigate(
                                R.id.action_homeFragment_to_detailFragment,bundle
                            )
                        }
                    }
                    is AllEvent.Failure -> {
                        with(binding) {
                            errorImage.visibility = View.VISIBLE
                            errorTxt.visibility = View.VISIBLE
                            errorTxt.text = all.errorText
                            errorTxt.setTextColor(Color.RED)
                            binding.swipe.isRefreshing = false
                        }
                        adapter.differ.submitList(emptyList())
                    }
                    is AllEvent.Loading -> {
                        with(binding){
                            swipe.isRefreshing = true
                            errorImage.visibility = View.GONE
                            errorTxt.visibility = View.GONE
                            errorTxt.visibility = View.GONE
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setupRecyclerView(){
        adapter = HomeAdapter()
        binding.genreList.adapter = adapter
        binding.genreList.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
