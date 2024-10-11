package com.example.store_api.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class UpcomingView :ViewModel() {
    private val _eventData = MutableLiveData<ArrayList<String>>()
    val eventData: MutableLiveData<ArrayList<String>> get() = _eventData

    private val _loading = MutableLiveData<Boolean>()
    val loading: MutableLiveData<Boolean> get() = _loading

    private val client = AsyncHttpClient()

    fun getEvent(){
        _loading.value = true
        val url = "https://event-api.dicoding.dev/events?active=1"
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
                    val listUpcoming = ArrayList<String>()
                    for (i in 0 until event.length()) {
                        val eventObject = event.getJSONObject(i)
                        val id = eventObject.getString("id")
                        val name = eventObject.getString("name")
                        val logo = eventObject.getString("imageLogo")
                        val category = eventObject.getString("category")
                        val imageUrl = eventObject.getString("mediaCover")
                        val item = "$name;$category;$logo;$imageUrl;$id"
                        listUpcoming.add(item)
                    }
                    _eventData.value = listUpcoming
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