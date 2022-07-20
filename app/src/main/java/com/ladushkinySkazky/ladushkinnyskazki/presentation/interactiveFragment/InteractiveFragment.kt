package com.ladushkinySkazky.ladushkinnyskazki.presentation.interactiveFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ladushkinySkazky.ladushkinnyskazki.databinding.FragmentInteractiveBinding

class InteractiveFragment : Fragment() {

    private var _binding: FragmentInteractiveBinding? = null
    private val binding: FragmentInteractiveBinding
        get() = _binding ?: throw RuntimeException("FragmentInteractiveBinding == null")

    private lateinit var viewModel: InteractiveViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInteractiveBinding.inflate(layoutInflater)
//        categoryAdapter = CategoryAdapter(binding.root.context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel = ViewModelProvider(this)[InteractiveViewModel::class.java]
//        viewModel.categoryList.observe(viewLifecycleOwner) {
//            categoryAdapter.submitList(it)
//            if (it.isNotEmpty()) {
//                binding.progress.visibility = View.GONE
//            }
//        }
    }

    companion object {
        fun newInstance(): InteractiveFragment {
            return InteractiveFragment()
        }
    }
}