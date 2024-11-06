package com.example.gym_app

import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Button
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class PlayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.play)


        val videoView: WebView = findViewById(R.id.webView)
        val videoURL = intent.getStringExtra("videoURL")
        if (videoURL != null)
            videoView.loadUrl(videoURL)
        videoView.settings.javaScriptEnabled = true
        videoView.webChromeClient = WebChromeClient()
        finish()
    }
}