package com.example.healthhub

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var text_title: TextView = itemView.findViewById(R.id.text_title)
    var text_source: TextView = itemView.findViewById(R.id.text_source)
    var img_headline: ImageView = itemView.findViewById(R.id.img_headline)
    var cardView: CardView = itemView.findViewById(R.id.main_container)
}
