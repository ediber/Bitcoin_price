package com.edi.test1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
//import jdk.nashorn.internal.runtime.ECMAErrors.getMessage
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class MainActivity : AppCompatActivity() {

    val BPI_ENDPOINT = "https://api.coindesk.com/v1/bpi/currentprice.json"
    val okHttpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView.setOnClickListener { v ->
            loadPrice() }
    }

    private fun loadPrice() {
        val request: Request = Request.Builder().url(BPI_ENDPOINT).build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) { }
            override fun onResponse(call: Call?, response: Response?) {
                val json = response?.body()?.string()
                // we get the json response returned by the Coin Desk API
                // make this call on a browser for example to watch the properties
                // here we get USD and EUR rates properties
                // we split the value got just to keep the integer part of the values
                val usdRate = (JSONObject(json).getJSONObject("bpi").getJSONObject("USD")["rate"] as String).split(".")[0]
                val eurRate = (JSONObject(json).getJSONObject("bpi").getJSONObject("EUR")["rate"] as String).split(".")[0]
                runOnUiThread {
                    txt.text = "$$usdRate\n\n$eurRate€"
                }
            }
        })
    }




}
