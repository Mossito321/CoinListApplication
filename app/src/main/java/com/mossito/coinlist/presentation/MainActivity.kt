package com.mossito.coinlist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mossito.coinlist.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initCoinListFragment()
    }

    private fun initCoinListFragment() {

    }
}