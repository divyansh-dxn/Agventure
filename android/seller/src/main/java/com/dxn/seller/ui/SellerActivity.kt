package com.dxn.seller.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dxn.seller.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SellerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}