package com.roy.devil.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bride.ui_lib.BaseFragment
import com.roy.devil.adapter.HomeAdapter
import com.roy.devil.databinding.FragmentHomeBinding
import com.roy.devil.repository.HomeRepository

class HomeFragment : BaseFragment() {
    private lateinit var mBinding: FragmentHomeBinding
    private val mHomeAdapter by lazy { HomeAdapter(requireActivity()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        assembleData()
    }

    private fun initView() {
        mBinding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        mBinding.recyclerView.adapter = mHomeAdapter
    }

    private fun assembleData() {
        mHomeAdapter.setList(HomeRepository.getAllModels())
    }
}