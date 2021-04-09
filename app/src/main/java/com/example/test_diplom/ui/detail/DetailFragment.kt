package com.example.test_diplom.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.test_diplom.databinding.DetailFragmentBinding

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
        binding.res.text = id.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}