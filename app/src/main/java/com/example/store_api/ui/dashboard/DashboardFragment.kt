package com.example.store_api.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
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
    private val listUpcoming = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.listUpcoming.layoutManager = LinearLayoutManager(requireContext())
        getData()
        return root
    }

    private fun getData(){
        binding.progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://event-api.dicoding.dev/events?active=1"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>?,
                responseBody: ByteArray
            ) {
                binding.progressBar.visibility = View.GONE
                val result = String(responseBody)
                try {
                    val jsonObject = JSONObject(result)
                    val message = jsonObject.getString("message")
                    val event = jsonObject.getJSONArray("listEvents")

                    for (i in 0 until event.length()){
                        val eventObject = event.getJSONObject(i)
                        val id = eventObject.getString("id")
                        val name = eventObject.getString("name")
                        val logo = eventObject.getString("imageLogo")
                        val category = eventObject.getString("category")
                        val imageUrl = eventObject.getString("mediaCover")

                        val item = "$name;$category;$logo;$imageUrl;$id;"
                        listUpcoming.add(item)
                    }
                    val adapter = AdapterList(listUpcoming)
                    binding.listUpcoming.adapter = adapter
                }catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }

    }override fun onFailure(
                statusCode: Int,
                headers: Array<Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}