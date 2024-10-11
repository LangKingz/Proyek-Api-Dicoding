package com.example.store_api.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.store_api.AdapterList
import com.example.store_api.databinding.FragmentDashboardBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val upComing : UpcomingView by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.listUpcoming.layoutManager = LinearLayoutManager(requireContext())

        upComing.eventData.observe(viewLifecycleOwner,Observer{
            list -> if (list != null){
                val adapter = AdapterList(list)
                binding.listUpcoming.adapter = adapter

        }
        })

        upComing.loading.observe(viewLifecycleOwner, Observer { load ->
            if (load) {
                binding.progressBar.visibility = View.VISIBLE
            } else binding.progressBar.visibility = View.GONE

        })

        upComing.getEvent()

        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}