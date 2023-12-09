package com.example.healthhub

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.healthhub.Models.NewHeadlines
import com.squareup.picasso.Picasso

class DetailsFragment : AppCompatActivity() {
    private lateinit var headLine: NewHeadlines
    private lateinit var txt_title: TextView
    private lateinit var txt_author: TextView
    private lateinit var txt_time: TextView
    private lateinit var txt_detail: TextView
    private lateinit var txt_content: TextView
    private lateinit var img_news: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_fragment)

        txt_title = findViewById(R.id.textTitle)
        txt_author = findViewById(R.id.textAuthor)
        txt_time = findViewById(R.id.textTime)
        txt_detail = findViewById(R.id.textDetail)
        txt_content = findViewById(R.id.textContent)
        img_news = findViewById(R.id.imgDetails)

        headLine = intent.getSerializableExtra("data") as NewHeadlines

        // Use 'headLine' to populate the UI elements or perform other actions
        if (headLine != null) {
            txt_title.text = headLine.title
            txt_author.text = headLine.author
            txt_time.text = headLine.publishedAt
            txt_detail.text = headLine.description
            txt_content.text = headLine.content
            Picasso.get().load(headLine.urlToImage).into(img_news)

            // Set other TextViews, ImageView, etc. based on the 'headLine' object
        }
    }
}
