package com.ladushkinySkazky.ladushkinnyskazki.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.databinding.FragmentMainBinding
import com.ladushkinySkazky.ladushkinnyskazki.listeners.RecyclerItemClickListener
import com.ladushkinySkazky.ladushkinnyskazki.presentation.adapters.CategoryAdapter
import com.ladushkinySkazky.ladushkinnyskazki.snake.SnakeActivity

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
        goSnake()
        rvSetup()
        onClickItem(binding.rvListCategory, binding.root.context)
        observeViewModel()
    }

    private fun goSnake() {
        binding.btnSnakeCategoryMain.setOnClickListener {
            val intent = Intent(requireActivity(), SnakeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun rvSetup() {
        with(binding.rvListCategory) {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(
                binding.root.context,
                RecyclerView.VERTICAL, false
            )
            setHasFixedSize(true)
            recycledViewPool.setMaxRecycledViews(50, 50)
            setItemViewCacheSize(50)
        }
    }

    private fun observeViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.categoryList.observe(viewLifecycleOwner) {
            categoryAdapter.submitList(it)
            if (it.isNotEmpty()) {
                binding.progress.visibility = View.GONE
            }
        }
    }

    private fun onClickItem(rv: RecyclerView, context: Context) {
        rv.addOnItemTouchListener(
            RecyclerItemClickListener(context, rv,
                object : RecyclerItemClickListener.OnItemClickListener {

                    override fun onItemClick(view: View, position: Int) {

                        val args = Bundle()
                        args.putInt("pos", position)

                        val skazkyFragment = SkazkyFragment.newInstance()
                        skazkyFragment.arguments = args
                        val manager = (activity as AppCompatActivity).supportFragmentManager
                        manager.beginTransaction()
                            .replace(R.id.containerSnake, skazkyFragment, args.toString())
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(this@MainFragment.toString())
                            .commit()
                    }

                    override fun onItemLongClick(view: View?, position: Int) {

                    }
                }
            )
        )
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}