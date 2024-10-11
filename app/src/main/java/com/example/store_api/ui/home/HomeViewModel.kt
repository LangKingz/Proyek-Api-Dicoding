package com.example.store_api.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class HomeViewModel : ViewModel() {
    private val _products = MutableLiveData<ArrayList<String>>()
    val products: LiveData<ArrayList<String>> get() = _products

    private val _loading = MutableLiveData<Boolean>()
    val loading :LiveData<Boolean> get() = _loading

    private val client = AsyncHttpClient()

    fun getProducts() {
        _loading.value = true
        val url = "https://event-api.dicoding.dev/events?active=0"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                _loading.value = false
                val result = String(responseBody!!)
                try {
                    val json = JSONObject(result)
                    val event = json.getJSONArray("listEvents")
                    val listReview = ArrayList<String>()

                    for(i in 0 until event.length()){
                        val eventObject = event.getJSONObject(i)
                        val id = eventObject.getString("id")
                        val name = eventObject.getString("name")
                        val logo = eventObject.getString("imageLogo")
                        val category = eventObject.getString("category")
                        val imageUrl = eventObject.getString("mediaCover")

                        val item = "$name;$category;$logo;$imageUrl;$id"
                        listReview.add(item)
                    }
                    _products.value = listReview
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                _loading.value = false
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        client.cancelAllRequests(true)

    }
}