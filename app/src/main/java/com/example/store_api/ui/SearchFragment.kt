package com.example.store_api.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.store_api.AdapterList
import com.example.store_api.R
import com.example.store_api.databinding.FragmentSearchBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class SearchFragment : Fragment() {

    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val listSearch = ArrayList<String>()
    private val client = AsyncHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater,container,false)
        binding.progressBar4.visibility = View.GONE
        binding.Text.text = "Tidak Ada Pencarian"
        val root : View = binding.root
        binding.searchRe.layoutManager = LinearLayoutManager(requireContext())
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null){
                    Log.d("SearchFragment", "onQueryTextSubmit: $query")
                    getSearch(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return root
    }

    private fun getSearch(query:String){
        binding.Text.visibility = View.GONE
        binding.progressBar4.visibility = View.VISIBLE
        val url = "https://event-api.dicoding.dev/events?active=-1&q=$query"
        val clients = AsyncHttpClient()
        clients.get(url,object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                binding.progressBar4.visibility = View.GONE
                val result = String(responseBody!!)
                try {
                    val json = JSONObject(result)
                    val event = json.getJSONArray("listEvents")
                    for (i in 0 until event.length()){
                        val eventObject = event.getJSONObject(i)
                        val id = eventObject.getString("id")
                        val name = eventObject.getString("name")
                        val logo = eventObject.getString("imageLogo")
                        val category = eventObject.getString("category")
                        val imageUrl = eventObject.getString("mediaCover")

                        val item = "$name;$category;$logo;$imageUrl;$id"
                        listSearch.add(item)
                    }
                    if(listSearch.isEmpty()) {
                        listSearch.clear()
                        binding.Text.visibility = View.VISIBLE
                        binding.Text.text = "Tidak Hasil Pencarian kamu :("
                    }else{
                        val adapter = AdapterList(listSearch)
                        binding.searchRe.adapter = adapter
                    }
                }catch (e:Exception){
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                binding.progressBar4.visibility = View.GONE
                Toast.makeText(requireContext(), "Error: ${error?.message}", Toast.LENGTH_SHORT).show()
            }
        })



    }

    override fun onDestroy() {
        super.onDestroy()
        client.cancelAllRequests(true)
        _binding = null
    }

}