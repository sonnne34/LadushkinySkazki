package com.ladushkinySkazky.ladushkinnyskazki.presentation.skazkyFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.ladushkinySkazky.ladushkinnyskazki.databinding.FragmentSkazkyBinding
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.SkazkiCatModel

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
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSkazkyBinding.inflate(layoutInflater)
        skazkiAdapter = SkazkiAdapter(binding.root.context)
        onClickItem()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvListSkazki.adapter = skazkiAdapter
        val isNewSkazky = args.newSkazky
        val position = args.position
        observeViewModel(isNewSkazky, position)

    }

    override fun onResume() {
        super.onResume()
        activity?.invalidateOptionsMenu()
    }

    private fun observeViewModel(isNewSkazky: Boolean, position: Int) {
        viewModel = ViewModelProvider(this)[SkazkyViewModel::class.java]
        viewModel.getItemSkazkiList(isNewSkazky, position)
        viewModel.skazkyList.observe(viewLifecycleOwner) {
            skazkiAdapter.submitList(it)
            if (it.isNotEmpty()) {
                binding.progress.visibility = View.GONE
            }
        }
    }

    private fun onClickItem() {
        skazkiAdapter.onSkazkyClickListener = object : SkazkiAdapter.OnSkazkyClickListener {
            override fun onSkazkyClick(skazkiCatModel: SkazkiCatModel, position: Int) {
                SkazkaTextDialog.openBody(requireActivity(), skazkiCatModel)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}