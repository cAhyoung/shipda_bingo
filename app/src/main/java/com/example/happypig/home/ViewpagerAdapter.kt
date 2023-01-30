package com.example.happypig.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.happypig.R

class ViewpagerAdapter (var adImgArray : ArrayList<Int>):
        RecyclerView.Adapter<ViewpagerAdapter.PagerViewHolder>() {

        // item을 넣을 공간 확보
    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.viewpager_adbanner, parent, false)) {
            val adBanner = itemView.findViewById<ImageView>(R.id.ad_img)
        }

    // ..? 정확히 무슨일을 함수인지는 이해하지 못함
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    // 이미지가 들어있는 array의 size를 얻음
    override fun getItemCount(): Int = adImgArray.size

    // 가져온 이미지들을 확보해둔 공간에 넣어주는 함수..?
    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.adBanner.setImageResource(adImgArray[position])
    }
}