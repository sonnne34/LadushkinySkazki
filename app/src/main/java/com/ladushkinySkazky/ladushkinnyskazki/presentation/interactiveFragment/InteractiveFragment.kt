package com.ladushkinySkazky.ladushkinnyskazki.presentation.interactiveFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ladushkinySkazky.ladushkinnyskazki.databinding.FragmentInteractiveBinding

class InteractiveFragment : Fragment() {

    private var _binding: FragmentInteractiveBinding? = null
    private val binding: FragmentInteractiveBinding
        get() = _binding ?: throw RuntimeException("FragmentInteractiveBinding == null")

    private lateinit var viewModel: InteractiveViewModel

    private lateinit var interactiveAdapter: InteractiveAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInteractiveBinding.inflate(layoutInflater)
        interactiveAdapter = InteractiveAdapter(binding.root.context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvListInteractive.adapter = interactiveAdapter
        goInteractiveAdd()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel = ViewModelProvider(this)[InteractiveViewModel::class.java]
        viewModel.interactiveList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.progressInteractive.visibility = View.GONE
            }
            interactiveAdapter.submitList(it.sortedByDescending { it1 -> it1.DataTime })
        }
    }

    private fun goInteractiveAdd() {
        binding.btnSendInteractive.setOnClickListener {
            findNavController().navigate(
                InteractiveFragmentDirections.actionInteractiveFragmentToInteractiveAddFragment()
            )
        }
    }
}