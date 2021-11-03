package com.example.ladushkinnyskazki.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ladushkinnyskazki.adapters.SkazkiAdapter
import com.example.ladushkinnyskazki.databinding.MainFragmentBinding
import com.example.ladushkinnyskazki.dialog.SkazkaBodyDialog
import com.example.ladushkinnyskazki.loaders.LoadFireBase
import com.example.ladushkinnyskazki.models.CategorySkazkiModel
import com.example.ladushkinnyskazki.models.SkazkiCatModel
import com.sushi.Sushi.listener.RecyclerItemClickListenr

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

        skazkiAdapter = SkazkiAdapter(inflater.context)

        binding.rvListSkazki.adapter = skazkiAdapter
        binding.rvListSkazki.layoutManager = LinearLayoutManager(binding.root.context,
            RecyclerView.VERTICAL,false)
        binding.rvListSkazki.setHasFixedSize(true)
        binding.rvListSkazki.recycledViewPool.setMaxRecycledViews(50, 50)
        binding.rvListSkazki.setItemViewCacheSize(50)

        LoadFireBase(inflater.context).loadSkazki(categorySkazkiList, skazkiAdapter)

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