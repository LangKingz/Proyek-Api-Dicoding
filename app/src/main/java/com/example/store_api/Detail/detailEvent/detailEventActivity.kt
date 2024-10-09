package com.example.store_api.Detail.detailEvent

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.store_api.MainActivity
import com.example.store_api.R
import com.example.store_api.databinding.ActivityDetailEventBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class detailEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEventBinding

    companion object{
        const val EXTRA_ID = "EXTRA_ID"

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getIntExtra(EXTRA_ID,0)

        getDataFromId(id)

        binding.btnBackHomeEvent.setOnClickListener{
            val back = Intent(this@detailEventActivity, MainActivity::class.java)
            startActivity(back)
        }

    }

    private fun  getDataFromId (id : Int){

        val client = AsyncHttpClient()
       val url = "https://event-api.dicoding.dev/events/$id"
        client.get(url,object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                // Jika koneksi berhasil

                val result = String(responseBody)

                try {
                    val json = JSONObject(result)
                    val res = json.getJSONObject("event")
                    val gambar = res.getString("mediaCover")
                    val nama = res.getString("name")
                    val deskripsi = res.getString("description")
                    val tanggal = res.getString("beginTime")
                    val link = res.getString("link")
                    val quota = res.getInt("quota")
                    val regis = res.getInt("registrants")
                    val owner = res.getString("ownerName")

                    var quotas = quota - regis
                    Glide.with(this@detailEventActivity)
                        .load(gambar)
                        .into(binding.imageEvent)
                    binding.judulEvent.text = nama
                    binding.deskripsievent.text = deskripsi
                    binding.waktu.text = tanggal

                    binding.btnKirimlinkEvent.setOnClickListener {
                        val openLinkIntent = Intent(Intent.ACTION_VIEW)
                        openLinkIntent.data = link?.toUri()
                        startActivity(openLinkIntent)
                    }

                    binding.quota.text = "Sisa Kouta : ${quotas.toString()}"
                    binding.owner.text = owner



                }catch (e: Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
//                binding.loading.visibility = View.GONE
                // Log the error or show a message to the user
                Log.e("DetailEventActivity", "Failed to fetch data: $error")
                Toast.makeText(this@detailEventActivity, "Failed to load event details", Toast.LENGTH_SHORT).show()
            }
        })
    }
}