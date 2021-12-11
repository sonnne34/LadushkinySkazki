package com.ladushkinySkazky.ladushkinnyskazki.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.adapters.CategoryAdapter
import com.ladushkinySkazky.ladushkinnyskazki.databinding.FragmentCategoryMainBinding
import com.ladushkinySkazky.ladushkinnyskazki.loaders.LoadFireBase
import com.ladushkinySkazky.ladushkinnyskazki.models.CategorySkazkiModel
import com.ladushkinySkazky.ladushkinnyskazki.listeners.RecyclerItemClickListener

class MainFragmentCategory : Fragment() {

    private lateinit var binding : FragmentCategoryMainBinding
    private var categoryAdapter: CategoryAdapter? = null
    private var categorySkazkiList: ArrayList<CategorySkazkiModel> = ArrayList()
    private lateinit var mainFragment: SkazkyFragment

    companion object {
        fun newInstance() = MainFragmentCategory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCategoryMainBinding.inflate(layoutInflater)

        val progress = binding.progress
        val rvListCategorySkazki = binding.rvListCategory

        if (categoryAdapter == null){
            categoryAdapter = CategoryAdapter(inflater.context)
            LoadFireBase().loadSkazki(categorySkazkiList, categoryAdapter!!, progress)
        } else{
            progress.visibility = View.GONE
        }

        rvListCategorySkazki.adapter = categoryAdapter
        rvListCategorySkazki.layoutManager = LinearLayoutManager(binding.root.context,
            RecyclerView.VERTICAL,false)
        rvListCategorySkazki.setHasFixedSize(true)
        rvListCategorySkazki.recycledViewPool.setMaxRecycledViews(50, 50)
        rvListCategorySkazki.setItemViewCacheSize(50)

        onClickItem(rvListCategorySkazki, binding.root.context)

        return binding.root

    }

    fun onClickItem(rv: RecyclerView, context: Context){
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
                            .replace(R.id.container, mainFragment, args.toString())
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(this@MainFragmentCategory.toString())
                            .commit()
                    }

                    override fun onItemLongClick(view: View?, position: Int) {

                    }
                }
            )
        )
    }
}