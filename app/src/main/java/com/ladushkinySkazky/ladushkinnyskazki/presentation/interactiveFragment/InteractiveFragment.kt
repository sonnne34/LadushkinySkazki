package com.ladushkinySkazky.ladushkinnyskazki.presentation.interactiveFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.databinding.FragmentInteractiveBinding
import com.ladushkinySkazky.ladushkinnyskazki.presentation.adapters.InteractiveAdapter
import com.ladushkinySkazky.ladushkinnyskazki.presentation.interactiveAddFragment.InteractiveAddFragment

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
        goInteractiveAdd()
        observeViewModel()
    }

    private fun goInteractiveAdd() {
        binding.btnSendInteractive.setOnClickListener {
            val interactiveAddFragment = InteractiveAddFragment.newInstance()
            val manager = (activity as AppCompatActivity).supportFragmentManager
            manager.beginTransaction()
                .replace(R.id.container, interactiveAddFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(InteractiveFragment.toString())
                .commit()
        }
    }

    private fun observeViewModel() {
        viewModel = ViewModelProvider(this)[InteractiveViewModel::class.java]
        viewModel.interactiveList.observe(viewLifecycleOwner) {
            interactiveAdapter.submitList(it)
            Log.d("LOAD", "interactiveAdapter = $it" )
        }
    }

    companion object {
        fun newInstance(): InteractiveFragment {
            return InteractiveFragment()
        }
    }
}