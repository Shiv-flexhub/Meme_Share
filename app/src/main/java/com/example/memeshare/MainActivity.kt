package com.example.memeshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.memeshare.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    var currenturl: String? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadMeme()
    }

    private fun loadMeme()
    {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        //val view_img: ImageView = findViewById(R.id.memeimageView) better to use binding tool

        val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                 currenturl = response.getString("url")
                Glide.with(this).load(currenturl).into(binding.memeimageView)
                },
                Response.ErrorListener  {
                        Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()
                })

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

    fun sharememe(view: View) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Checkout this cool meme $currenturl")
        val chooser = Intent.createChooser(intent, "share this meme via: ")
        startActivity(chooser)

    }
    fun nextmeme(view: View) {
        loadMeme()
    }
}