package com.example.test_diplom

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MainActivity::class.java))
        Thread.sleep(1000)
        finish()
    }
}