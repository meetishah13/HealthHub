package com.example.healthhub

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.healthhub.Models.NewHeadlines
import com.squareup.picasso.Picasso

public class CustomAdapter(private val context: Context, private val headlines: List<NewHeadlines>, private val selectListener: SelectListener) : RecyclerView.Adapter<CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.headline_list_items, parent, false)
        return CustomViewHolder(view)
    }
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        // Bind your data to the views in the ViewHolder here
        // For example:
        // holder.text_title.text = "Your Title"
        // holder.text_source.text = "Your Source"
        // Load image using a library like Picasso or Glide
        // Picasso.get().load("your_image_url").into(holder.img_headline)
        holder.text_title.setText(headlines.get(position).title)
        holder.text_source.setText(headlines.get(position).source?.name)
        if (headlines[position].urlToImage != null) {
            Picasso.get().load(headlines[position].urlToImage).into(holder.img_headline)
        }
        holder.cardView.setOnClickListener {
            selectListener?.onNewsClicked(headlines[position])
        }
    }

    override fun getItemCount(): Int {
        // Return the size of your data set
        // For example: return headlines.size
        return headlines.size
    }
}
