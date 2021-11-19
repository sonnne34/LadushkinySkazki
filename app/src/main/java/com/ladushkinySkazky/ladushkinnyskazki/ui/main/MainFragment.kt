package com.ladushkinySkazky.ladushkinnyskazki.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ladushkinySkazky.ladushkinnyskazki.adapters.SkazkiAdapter
import com.ladushkinySkazky.ladushkinnyskazki.databinding.MainFragmentBinding
import com.ladushkinySkazky.ladushkinnyskazki.dialog.SkazkaBodyDialog
import com.ladushkinySkazky.ladushkinnyskazki.loaders.LoadFireBase
import com.ladushkinySkazky.ladushkinnyskazki.models.CategorySkazkiModel
import com.ladushkinySkazky.ladushkinnyskazki.models.SkazkiCatModel
import com.sushi.Sushi.listener.RecyclerItemClickListenr
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {


    private lateinit var binding : MainFragmentBinding
    private lateinit var skazkiAdapter: SkazkiAdapter
    private var categorySkazkiList: ArrayList<CategorySkazkiModel> = ArrayList()

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(layoutInflater)

        val progress = binding.progress

        skazkiAdapter = SkazkiAdapter(inflater.context)

        val rvListSkazki = binding.rvListSkazki

        rvListSkazki.adapter = skazkiAdapter
        rvListSkazki.layoutManager = LinearLayoutManager(binding.root.context,
            RecyclerView.VERTICAL,false)
        rvListSkazki.setHasFixedSize(true)
        rvListSkazki.recycledViewPool.setMaxRecycledViews(50, 50)
        rvListSkazki.setItemViewCacheSize(50)

        LoadFireBase(inflater.context).loadSkazki(categorySkazkiList, skazkiAdapter, progress)

        return binding.root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
//    }

    private fun itemClick(context: Context, skazkiCatModel: SkazkiCatModel){
        binding.rvListSkazki.addOnItemTouchListener(
            RecyclerItemClickListenr(context, binding.rvListSkazki,
                object : RecyclerItemClickListenr.OnItemClickListener {

                    override fun onItemClick(view: View, position: Int) {

                        SkazkaBodyDialog.openBody(context, skazkiCatModel)
                    }

                    override fun onItemLongClick(view: View?, position: Int) {

                    }
                })
        )
    }
}