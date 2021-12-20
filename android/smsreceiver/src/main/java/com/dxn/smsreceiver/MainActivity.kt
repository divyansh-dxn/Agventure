package com.dxn.smsreceiver

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.widget.TextView
import androidx.core.app.ActivityCompat
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

    private fun receiveMsg() {
        val br = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                for (sms in Telephony.Sms.Intents.getMessagesFromIntent(p1)) {
                    findViewById<TextView>(R.id.editText).text = sms.originatingAddress.toString()
                    findViewById<TextView>(R.id.editText2).text = sms.displayMessageBody.toString()
                    val lineCount: Int = findViewById<TextView>(R.id.editText2).lineCount
                    findViewById<TextView>(R.id.editText3).text = lineCount.toString()
                    val strings: List<String> = sms.displayMessageBody.split(" ", "\n")
                    for (i in 0 until (strings.size) step 3) {
                        val usr = CatalogueProduct(
                            sellerId = sms.displayOriginatingAddress.toString(),
                            productId = strings[i],
                            price = strings[i + 1].toFloat(),
                            quantity = strings[i + 2].toInt()
                        )
                        firestore.collection("products_collection").document().set(usr)
                    }
                }
            }
        }
        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }
}