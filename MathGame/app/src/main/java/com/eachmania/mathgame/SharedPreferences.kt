package com.eachmania.mathgame

import android.content.Context
import android.content.SharedPreferences


class SharedPreferences {

    companion object {
        private const val APP_SETTINGS_FILE : String = "APP_SETTINGS"
    }

    fun getSharedPreferences(): SharedPreferences? {
        return MyApp.getContext().getSharedPreferences(
           APP_SETTINGS_FILE,
            Context.MODE_PRIVATE
        )
    }

    fun setSomeStringValue(dataLabel: String?, dataValue: String?) {

        val sharedPref = getSharedPreferences() ?: return
        with (sharedPref.edit()) {
            putString(dataLabel, dataValue)
            apply()
        }

        val sharedPref2 = getSharedPreferences()?.edit()
        sharedPref2?.apply {
            putString(dataLabel,dataValue)
            apply()
        }

    }

    fun getSomeStringValue(context:Context,dataLabel: String?): String? {
        return getSharedPreferences()?.getString(dataLabel,null)
    }


}