package com.example.shoesstore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.request.RequestOptions
import com.example.shoesstore.R
import com.example.shoesstore.model.SliderModel

class SliderAdapter(private var sliderItem: List<SliderModel>,
                    private val viewPager2: ViewPager2) :
    RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {
    private lateinit var context: Context
    private val runnable = Runnable {
        sliderItem = sliderItem
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slider_item_container, parent, false)
        return SliderViewHolder(view)
    }
    override fun getItemCount(): Int = sliderItem.size
    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.setImage(sliderItem[position], context)
        if (position == sliderItem.lastIndex) {
            viewPager2.post(runnable)
        }
    }
    class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageSlider)
        fun setImage(sliderItem: SliderModel, context: Context) {
            val requestOptions = RequestOptions().transform(CenterInside())
            Glide.with(context).load(sliderItem.url).apply(requestOptions).into(imageView)
        }

    }
}