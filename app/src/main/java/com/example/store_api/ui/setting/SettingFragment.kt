package com.example.store_api.ui.setting

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.example.store_api.R
import com.example.store_api.data.settingPReference
import com.example.store_api.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {

    private var _binding:FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private lateinit var themeManager: settingPReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        themeManager = settingPReference(requireContext())

        if(themeManager.loadTheme()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        _binding = FragmentSettingBinding.inflate(inflater,container,false)

        binding.btnTheme.setOnClickListener{
            val isDarkMode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
            themeManager.setTheme(!isDarkMode)

            if(isDarkMode){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }


        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}