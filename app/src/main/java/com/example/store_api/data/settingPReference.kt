package com.example.store_api.data

import android.content.Context
import android.content.SharedPreferences

class settingPReference(private val context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("setting",Context.MODE_PRIVATE)

    companion object {
        private const val PREF_KEY_THEME = "PREF_KEY_THEME"
        private const val THEME_LIGHT = "light"
        private const val THEME_DARK = "dark"

    }

    fun setTheme(isDarkmode: Boolean){
        val theme = if(isDarkmode) THEME_DARK else THEME_LIGHT
        prefs.edit().putString(PREF_KEY_THEME,theme).apply()
    }

    fun loadTheme():Boolean{
        val theme = prefs.getString(PREF_KEY_THEME,THEME_LIGHT)
        return theme == THEME_DARK
    }

}