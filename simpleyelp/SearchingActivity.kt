package com.example.simpleyelp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_searching.*


class SearchingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searching)

        btnSearch.setOnClickListener{
            if (etCuisine.text.isBlank() || etLocation.text.isBlank()) {
                Toast.makeText(this, "Please enter both the attributes", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val i = Intent(this, MainActivity::class.java)
            i.putExtra("cuisine", etCuisine.text.toString())
            i.putExtra("location", etLocation.text.toString())
            Toast.makeText(this, "Searching the best", Toast.LENGTH_SHORT).show()
            startActivity(i)
        }

    }
}