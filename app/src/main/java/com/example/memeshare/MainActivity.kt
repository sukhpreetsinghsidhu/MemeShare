package com.example.memeshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    lateinit var memeImageView :  ImageView
    lateinit var nextButton : Button
    lateinit var shareButton : Button
    lateinit var progressBar : ProgressBar

    var currentImageURL : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        memeImageView = findViewById(R.id.memeImageView)
        nextButton = findViewById(R.id.nextButton)
        shareButton = findViewById(R.id.shareButton)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE

        loadMeme()
        nextButton.setOnClickListener{
            loadMeme()
        }

        shareButton.setOnClickListener{
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,"Checkout this meme $currentImageURL")
            val chooser = Intent.createChooser(intent,"Share this meme using- ")
            startActivity(chooser)
        }


    }

    fun loadMeme(){

        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener{ response ->
                currentImageURL = response.getString("url")
                Glide.with(this).load(currentImageURL).into(memeImageView)
                println("in the response listnener")
            },
            Response.ErrorListener {
                Toast.makeText(this,"Something went wrong", Toast.LENGTH_LONG).show()
            })
        queue.add(jsonObjectRequest)
    }
}