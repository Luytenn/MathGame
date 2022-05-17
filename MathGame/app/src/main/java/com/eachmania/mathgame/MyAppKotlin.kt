package com.eachmania.mathgame

import android.app.Application
import android.content.Context
import android.util.Log

class MyAppKotlin : Application() {


    companion object {
        private var instance: Context ?= null

        fun getContext() : Context? {

            Log.d("instancia del contexto ", instance.toString())
            return instance

        }

    }

    override fun onCreate() {
        super.onCreate()
         instance  = applicationContext

    }



}