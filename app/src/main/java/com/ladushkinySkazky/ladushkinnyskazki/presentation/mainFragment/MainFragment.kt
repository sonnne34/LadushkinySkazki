package com.ladushkinySkazky.ladushkinnyskazki.presentation.mainFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        categoryAdapter = CategoryAdapter(binding.root.context)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvListCategory.adapter = categoryAdapter
        observeViewModel()
        onClickItem()
        goSnake()
        goInteractive()
        goNewSkazky()
    }

    private fun observeViewModel() {
        viewModel = ViewModelProvider(this.requireActivity())[MainViewModel::class.java]
        viewModel.categoryList.observe(this.requireActivity()) {
            if (it.isNotEmpty()) {
                binding.progress.visibility = View.GONE
                binding.btnNewCategoryMain.visibility = View.VISIBLE
            }
            categoryAdapter.submitList(it)
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

    private fun goSnake() {
        binding.btnSnakeCategoryMain.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToSnakeActivity()
            )
        }
    }

    private fun goInteractive() {
        binding.btnInteractiveCategoryMain.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToInteractiveFragment()
            )
        }
    }

    private fun goNewSkazky() {
        binding.btnNewCategoryMain.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToSkazkyFragment(true, 0)
            )
        }
    }
}