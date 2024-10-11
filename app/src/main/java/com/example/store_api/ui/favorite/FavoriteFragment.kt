package com.example.store_api.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.store_api.AdapterFav
import com.example.store_api.R
import com.example.store_api.data.room.FavoriteDao
import com.example.store_api.data.sqlite.FavoriteDatabase
import com.example.store_api.databinding.FragmentFavoriteBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteEventDao : FavoriteDao
    private lateinit var adapterFav : AdapterFav

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        val db = FavoriteDatabase.getDatabase(requireContext())
        binding.fav.layoutManager = LinearLayoutManager(requireContext())
        favoriteEventDao = db.favoriteDao()
        loadFavoriteEvents()

        return binding.root
    }

    private fun loadFavoriteEvents() {
        CoroutineScope(Dispatchers.IO).launch {
            val favoriteList = favoriteEventDao.getAll()
            withContext(Dispatchers.Main) {
                adapterFav = AdapterFav(favoriteList)
                binding.fav.adapter = adapterFav
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }




}