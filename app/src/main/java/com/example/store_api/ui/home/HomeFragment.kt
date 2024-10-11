package com.example.store_api.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.store_api.AdapterList
import com.example.store_api.databinding.FragmentHomeBinding



class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeView : HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        homeView.products.observe(viewLifecycleOwner, Observer { listReview ->
            if(listReview != null){
                val adapter = AdapterList(listReview)
                binding.recyclerView.adapter = adapter
            }
        })

        homeView.loading.observe(viewLifecycleOwner, Observer { loading ->
            if(loading){
                binding.loading1.visibility = View.VISIBLE
            }else{
                binding.loading1.visibility = View.GONE
            }
        })

        homeView.getProducts()

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}