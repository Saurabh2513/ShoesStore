package com.example.shoesstore.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.shoesstore.adapter.BrandAdapter
import com.example.shoesstore.adapter.PopularAdapter
import com.example.shoesstore.adapter.SliderAdapter
import com.example.shoesstore.databinding.ActivityMainBinding
import com.example.shoesstore.model.SliderModel
import com.example.shoesstore.network.SharedPreferencesHelper
import com.example.shoesstore.view.bottomnavitem.CartActivity
import com.example.shoesstore.view.bottomnavitem.ProfileActivity
import com.example.shoesstore.viewpmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : BaseActivity() {
    private val viewModel = MainViewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        sharedPreferencesHelper = SharedPreferencesHelper(this)


        initBanner()
        initBrand()
        initPopular()
        bottomNavClick()


        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val currentUser = auth.currentUser
        currentUser?.let {
            db.collection("users").document(it.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val email = document.getString("email")
                        binding.userName.text = "User Email: $email"
                    } else {
                        binding.userName.text = "No such user"
                    }
                }
                .addOnFailureListener {
                    binding.userName.text = "Error getting user"
                }
        }

    }

    private fun bottomNavClick() {
        binding.profileBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, ProfileActivity::class.java)
            startActivity(intent)
        }
        binding.cartBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, CartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initBanner() {
        binding.progressBarBanner.visibility = View.VISIBLE
        viewModel.banners.observe(this, Observer { items ->
            banners(items)
            binding.progressBarBanner.visibility = View.GONE
        })
        viewModel.loadBanners()
    }

    private fun banners(images: List<SliderModel>) {
        binding.viewPagerSlider.adapter = SliderAdapter(images, binding.viewPagerSlider)
        binding.viewPagerSlider.clipToPadding = false
        binding.viewPagerSlider.clipChildren = false
        binding.viewPagerSlider.offscreenPageLimit = images.size
        binding.viewPagerSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransform = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))

        }
        binding.viewPagerSlider.setPageTransformer(compositePageTransform)
        if (images.size > 1) {
            binding.dotIndicator.visibility = View.VISIBLE
            binding.dotIndicator.attachTo(binding.viewPagerSlider)
        }
    }

    private fun initBrand() {
        binding.progressBarBrand.visibility = View.VISIBLE
        viewModel.brands.observe(this, Observer {
            binding.viewBrand.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            binding.viewBrand.adapter = BrandAdapter(it)
            binding.progressBarBrand.visibility = View.GONE

        })
        viewModel.loadBrand()
    }

    private fun initPopular() {
        binding.progressBarPopular.visibility = View.VISIBLE
        viewModel.popular.observe(this, Observer {
            binding.viewPupolar.layoutManager = GridLayoutManager(this@MainActivity, 2)
            binding.viewPupolar.adapter = PopularAdapter(it)
            binding.progressBarPopular.visibility = View.GONE

        })
        viewModel.loadPopular()
    }
}