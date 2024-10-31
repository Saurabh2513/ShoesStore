package com.example.shoesstore.view.bottomnavitem

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shoesstore.databinding.ActivityProfileBinding
import com.example.shoesstore.view.activity.LogInActivity

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.logout.setOnClickListener {
            val intent = Intent(this,LogInActivity::class.java)
            startActivity(intent)
        }
    }
}