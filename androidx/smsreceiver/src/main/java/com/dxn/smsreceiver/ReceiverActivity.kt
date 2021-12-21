package com.dxn.smsreceiver

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.dxn.data.models.CatalogueProduct
import com.dxn.data.models.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.lang.IndexOutOfBoundsException

class ReceiverActivity : AppCompatActivity() {

    private var MY_PERMISSIONS_REQUEST_RECEIVE_SMS: Int = 0
    var products: List<Product> = listOf()

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
        firestore = FirebaseFirestore.getInstance()
        lifecycleScope.launchWhenCreated {
            products = firestore.collection("products").get().await().toObjects(Product::class.java)
            Log.d(TAG, "onCreate: ${products}")
        }
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
        } else {
            receiveMsg()
        }

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
                        try {
                            Log.d(TAG, "onReceive: ${strings[i]}")
                            Log.d(TAG, "onReceive: ${products[i]}")
                            val product = products.filter {
                                it.id == strings[i]
                            }[0]
                            val catalogueProduct = CatalogueProduct(
                                sellerId = sms.displayOriginatingAddress.toString(),
                                productId = product.id,
                                name = product.name,
                                photoUrl = product.photoUrl,
                                price = strings[i + 1].toFloat(),
                                quantity = strings[i + 2].toInt()
                            )
                            firestore.collection("products_collection").document().set(catalogueProduct)
                        } catch (e:IndexOutOfBoundsException) {
                            Log.d(TAG, "onReceive: INVALID PRODUCT ID RECEIVED")
                            Toast.makeText(applicationContext,"Error! Check your internet connection.",Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }
        }
        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }

    companion object{
        const val TAG = "MAIN_ACTIVITY"
    }
}