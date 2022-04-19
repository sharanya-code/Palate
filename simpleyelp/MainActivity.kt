package com.example.simpleyelp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "MainActivity"
private const val BASE_URL = "https://api.yelp.com/v3/"
private const val API_kEY = "5SfSIuBsHksupvO3tZTB6l6R25XczpKZmCg2o6k3hq2-PjfGPYIdojLN3zveXwtedM3wshU6fVAKNrDEFbw3yv0NVMYNVPKGuaU2FgXNgKTKNX1bfXJXqMWEvZ1XYnYx"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val restaurants = mutableListOf<YelpRestaurants>()
        val adapter = RestaurantsAdapter(this, restaurants)
        rvRestaurants.adapter = adapter
        rvRestaurants.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val yelpService = retrofit.create(YelpService::class.java)


        fabCreate.setOnClickListener{
            val intent = Intent(this, SearchingActivity::class.java )
            startActivity(intent)
        }

        yelpService.searchRestraunts("Bearer $API_kEY","soup", "new york").enqueue(object: Callback<YelpSearchResults>{
            override fun onResponse(call: Call<YelpSearchResults>, response: Response<YelpSearchResults>) {
                Log.i(TAG, "on response $response")
                val body = response.body()
                if (body == null){
                    Log.w(TAG, "No data received from yelp API")
                    return
                }
                restaurants.addAll(body.restaurants)
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<YelpSearchResults>, t: Throwable) {
                Log.i(TAG, "on response $t")
            }

        })

        val extras = intent.extras
        if (extras != null) {
            val city = extras.getString("location")
            val cuisine = extras.getString("cuisine")
            if (cuisine != null) {
                if (city != null) {
                    yelpService.searchRestraunts("Bearer $API_kEY",cuisine, city).enqueue(object: Callback<YelpSearchResults>{
                        override fun onResponse(call: Call<YelpSearchResults>, response: Response<YelpSearchResults>) {
                            Log.i(TAG, "on response $response")
                            val body = response.body()
                            if (body == null){
                                Log.w(TAG, "No data received from yelp API")
                                return
                            }
                            restaurants.addAll(body.restaurants)
                            adapter.notifyDataSetChanged()
                        }

                        override fun onFailure(call: Call<YelpSearchResults>, t: Throwable) {
                            Log.i(TAG, "on response $t")
                        }

                    })
                }
            }

        }



    }
}