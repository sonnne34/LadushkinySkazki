package com.ladushkinySkazky.ladushkinnyskazki.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ladushkinySkazky.ladushkinnyskazki.singletons.SkazkiSingleton
import com.ladushkinySkazky.ladushkinnyskazki.presentation.adapters.SkazkiAdapter
import com.ladushkinySkazky.ladushkinnyskazki.data.CategorySkazkiModel
import com.ladushkinySkazky.ladushkinnyskazki.databinding.FragmentSkazkyBinding

class SkazkyFragment : Fragment() {

    private lateinit var binding: FragmentSkazkyBinding
    private var skazkiAdapter: SkazkiAdapter? = null
    private var categorySkazkiList: ArrayList<CategorySkazkiModel> = ArrayList()
    private lateinit var categorySkazkiListSingleton: ArrayList<CategorySkazkiModel>
    private lateinit var model: CategorySkazkiModel

    companion object {
        fun newInstance() = SkazkyFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSkazkyBinding.inflate(layoutInflater)

        val progress = binding.progress
        val rvListSkazki = binding.rvListSkazki
        val position = arguments?.getString("pos")?.toInt()

        categorySkazkiListSingleton = SkazkiSingleton.skazkiItem
        model = categorySkazkiListSingleton[position!!]
        categorySkazkiList.add(model)

        if (skazkiAdapter == null) {
            skazkiAdapter = SkazkiAdapter(inflater.context)
            updateAdapter(categorySkazkiList, skazkiAdapter!!, progress)
        }

        rvListSkazki.adapter = skazkiAdapter
        rvListSkazki.layoutManager = LinearLayoutManager(
            binding.root.context,
            RecyclerView.VERTICAL, false
        )
        rvListSkazki.setHasFixedSize(true)
        rvListSkazki.recycledViewPool.setMaxRecycledViews(50, 50)
        rvListSkazki.setItemViewCacheSize(50)

        return binding.root
    }

    private fun updateAdapter(
        list: ArrayList<CategorySkazkiModel>,
        skazkiAdapter: SkazkiAdapter,
        progress: ProgressBar,
    ) {
        skazkiAdapter.setupSkazkiAdapter(list)
        progress.visibility = View.GONE
    }
}