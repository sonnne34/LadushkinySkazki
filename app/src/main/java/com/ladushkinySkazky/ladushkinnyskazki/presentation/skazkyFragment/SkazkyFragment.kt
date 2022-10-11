package com.ladushkinySkazky.ladushkinnyskazki.presentation.skazkyFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ladushkinySkazky.ladushkinnyskazki.databinding.FragmentSkazkyBinding

class SkazkyFragment : Fragment() {

    private var _binding: FragmentSkazkyBinding? = null
    private val binding: FragmentSkazkyBinding
        get() = _binding ?: throw RuntimeException("FragmentSkazkyBinding == null")

    private val args by navArgs<SkazkyFragmentArgs>()

    private lateinit var skazkiAdapter: SkazkiAdapter

    private lateinit var viewModel: SkazkyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSkazkyBinding.inflate(layoutInflater)
        skazkiAdapter = SkazkiAdapter(binding.root.context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvSetup()

        when (args.newSkazky) {
            true -> observeViewModelTypeNew()
            false -> observeViewModelTypeCategory()
        }
    }

    private fun rvSetup() {
        with(binding.rvListSkazki) {
            adapter = skazkiAdapter
            layoutManager = LinearLayoutManager(
                binding.root.context,
                RecyclerView.VERTICAL, false
            )
            setHasFixedSize(true)
            recycledViewPool.setMaxRecycledViews(50, 50)
            setItemViewCacheSize(50)
        }
    }

    private fun observeViewModelTypeCategory() {
        val position = args.position
        viewModel = ViewModelProvider(this)[SkazkyViewModel::class.java]
        viewModel.getItemSkazkiList(position)
        viewModel.skazkyList.observe(viewLifecycleOwner) {
            skazkiAdapter.submitList(it)
            if (it.isNotEmpty()) {
                binding.progress.visibility = View.GONE
            }
        }
    }

    private fun observeViewModelTypeNew() {
        viewModel = ViewModelProvider(this)[SkazkyViewModel::class.java]
        viewModel.getNewItemSkazkiList()
        viewModel.skazkyList.observe(viewLifecycleOwner) {
            skazkiAdapter.submitList(it)
            if (it.isNotEmpty()) {
                binding.progress.visibility = View.GONE
            }
        }
    }
}