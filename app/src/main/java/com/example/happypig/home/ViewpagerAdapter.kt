package com.example.happypig.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.happypig.R

class ViewpagerAdapter (var adImgArray : ArrayList<Int>):
        RecyclerView.Adapter<ViewpagerAdapter.PagerViewHolder>() {

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.viewpager_adbanner, parent, false)) {
            val adBanner = itemView.findViewById<ImageView>(R.id.ad_img)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun getItemCount(): Int = adImgArray.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.adBanner.setImageResource(adImgArray[position])
    }
}