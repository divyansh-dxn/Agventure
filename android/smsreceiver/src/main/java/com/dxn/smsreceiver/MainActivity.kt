package com.dxn.smsreceiver

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dxn.data.models.CatalogueProduct
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private var MY_PERMISSIONS_REQUEST_RECEIVE_SMS: Int = 0

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firestore = FirebaseFirestore.getInstance()
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.SEND_SMS
                ),
                111
            )
        } else
            receiveMsg()

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            receiveMsg()
        }
    }

    fun receiveMsg() {
        var br = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    for (sms in Telephony.Sms.Intents.getMessagesFromIntent(p1)) {
                        findViewById<TextView>(R.id.editText).text = sms.originatingAddress.toString()
                        findViewById<TextView>(R.id.editText2).text = sms.displayMessageBody.toString()
                        val lineCount: Int = findViewById<TextView>(R.id.editText2).lineCount
                        findViewById<TextView>(R.id.editText3).text = lineCount.toString()
                        val strs: List<String> = sms.displayMessageBody.split(" ", "\n")
                        for (i in 0 until (strs.size) step 3) {
                            val usr = CatalogueProduct(
                                sellerId = sms.displayOriginatingAddress.toString(),
                                productId = strs[i],
                                price = strs[i + 1].toFloat(),
                                quantity = strs[i + 2].toInt()
                            )
                            firestore.collection("products_collection").document().set(usr)
                        }
                    }
                }
            }
        }
        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }
}