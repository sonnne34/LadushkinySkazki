package com.ladushkinySkazky.ladushkinnyskazki.skazky.presentation.mainFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ladushkinySkazky.ladushkinnyskazki.databinding.FragmentMainBinding
import com.ladushkinySkazky.ladushkinnyskazki.skazky.domian.models.CategorySkazkiModel

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
        observeViewModel()
        onClickListeners()
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
            if (it.isNotEmpty()) {
                binding.progress.visibility = View.GONE
                binding.llButtonsMain.visibility = View.VISIBLE
            }
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}