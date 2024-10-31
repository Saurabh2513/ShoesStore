package com.example.shoesstore.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.shoesstore.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashScreenBinding
    private val splashTimeOut: Long = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed({
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }, splashTimeOut)
    }

    //       Handler().postDelayed({
//            val auth = FirebaseAuth.getInstance()
//            val currentUser = auth.currentUser
//            if (currentUser != null) {
//                val mainIntent = Intent(this, MainActivity::class.java)
//                startActivity(mainIntent)
//            } else {
//                val loginIntent = Intent(this, IntroActivity::class.java)
//                startActivity(loginIntent)
//            }
//            finish()
//        }, splashTimeOut)
//    }
}