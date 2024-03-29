package com.ladushkinySkazky.ladushkinnyskazki.presentation.mainFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.databinding.FragmentMainBinding
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.CategorySkazkiModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding == null")
    private lateinit var viewModel: MainViewModel
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        categoryAdapter = CategoryAdapter(binding.root.context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvListCategory.adapter = categoryAdapter
        onBackPress()
        observeViewModel()
        onClickListeners()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun onClickListeners() {
        btnGoSnake()
        btnGoInteractive()
        btnGoNewSkazky()
        onClickItem()
    }

    private fun observeViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.categoryList.observe(viewLifecycleOwner) {
            categoryAdapter.submitList(it)
            binding.progress.isVisible = it.isEmpty()
            binding.llButtonsMain.isVisible = it.isNotEmpty()
        }
    }

    private fun btnGoSnake() {
        binding.btnSnakeCategoryMain.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToSnakeActivity()
            )
        }
    }


    private fun btnGoInteractive() {
        binding.btnInteractiveCategoryMain.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToInteractiveFragment()
            )
        }
    }

    private fun btnGoNewSkazky() {
        binding.btnNewCategoryMain.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToSkazkyFragment(true, 0)
            )
        }
    }

    private fun onClickItem() {
        categoryAdapter.onCategoryClickListener = object : CategoryAdapter.OnCategoryClickListener {
            override fun onCategoryClick(categorySkazkiModel: CategorySkazkiModel, position: Int) {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToSkazkyFragment(false, position)
                )
            }
        }
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun onBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    AlertDialog.Builder(requireActivity())
                        .setTitle(getString(R.string.dialog_close_app))
                        .setPositiveButton(getString(R.string.dialog_yes)) { _, _ ->
                            requireActivity().finish()
                        }
                        .setNegativeButton(getString(R.string.dialog_no)) { _, _ -> }
                        .create()
                        .show()
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}