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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.presentation.adapters.CategoryAdapter
import com.ladushkinySkazky.ladushkinnyskazki.listeners.RecyclerItemClickListener
import com.ladushkinySkazky.ladushkinnyskazki.data.loaders.LoadFireBase
import com.ladushkinySkazky.ladushkinnyskazki.data.models.CategorySkazkiModel
import com.ladushkinySkazky.ladushkinnyskazki.databinding.FragmentMainBinding
import com.ladushkinySkazky.ladushkinnyskazki.snake.SnakeActivity

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private var categoryAdapter: CategoryAdapter? = null
    private var categorySkazkiList: ArrayList<CategorySkazkiModel> = ArrayList()
    private lateinit var mainFragment: SkazkyFragment

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainBinding.inflate(layoutInflater)

        //игра змейка
        val btnSnake = binding.btnSnakeCategoryMain
        btnSnake.setOnClickListener {
            val intent = Intent(inflater.context, SnakeActivity::class.java)
            startActivity(intent)
        }

        val progress = binding.progress
        val rvListCategorySkazki = binding.rvListCategory

        if (categoryAdapter == null) {
            categoryAdapter = CategoryAdapter(inflater.context)
            LoadFireBase().loadSkazki(categorySkazkiList, categoryAdapter!!, progress)
        } else {
            progress.visibility = View.GONE
        }

        rvListCategorySkazki.adapter = categoryAdapter
        rvListCategorySkazki.layoutManager = LinearLayoutManager(
            binding.root.context,
            RecyclerView.VERTICAL, false
        )
        rvListCategorySkazki.setHasFixedSize(true)
        rvListCategorySkazki.recycledViewPool.setMaxRecycledViews(50, 50)
        rvListCategorySkazki.setItemViewCacheSize(50)

        onClickItem(rvListCategorySkazki, binding.root.context)

        return binding.root

    }

    private fun onClickItem(rv: RecyclerView, context: Context) {
        rv.addOnItemTouchListener(
            RecyclerItemClickListener(context, rv,
                object : RecyclerItemClickListener.OnItemClickListener {

                    override fun onItemClick(view: View, position: Int) {

                        val args = Bundle()
                        args.putString("pos", position.toString())

                        mainFragment = SkazkyFragment.newInstance()
                        mainFragment.arguments = args
                        val manager = (activity as AppCompatActivity).supportFragmentManager
                        manager.beginTransaction()
                            .replace(R.id.containerSnake, mainFragment, args.toString())
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
}