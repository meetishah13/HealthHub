package com.example.healthhub

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthhub.Models.NewHeadlines
import com.example.healthhub.Models.NewsApiResponse

class HealthTipsAndArticlesActivity : AppCompatActivity(), OnFetchDataListener<NewsApiResponse>, SelectListener {

    private lateinit var manager: RequestManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapter
    private lateinit var dialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_tips_and_articles)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()?.setDisplayShowHomeEnabled(true);

        dialog = ProgressDialog(this)
        dialog.setTitle("Fetching news articles...")
        dialog.show()
        manager = RequestManager(this)
        // Assuming you have appropriate values for category and query
        manager.getNewsHeadlines(this, "health", "")
    }

    override fun onFetchData(list: List<NewHeadlines>, message: String) {
        // Handle fetched data
        showNews(list)
        dialog.dismiss()
    }
    private fun showNews(list: List<NewHeadlines>) {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        adapter = CustomAdapter(this, list, this)
        recyclerView.adapter = adapter
    }
    override fun onError(message: String) {
        // Handle error
    }
    override fun onNewsClicked(headline: NewHeadlines) {
        val intent = Intent(this@HealthTipsAndArticlesActivity, DetailsFragment::class.java)
        intent.putExtra("data", headline)
        startActivity(intent)

    }
}