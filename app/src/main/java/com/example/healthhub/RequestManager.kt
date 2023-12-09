package com.example.healthhub

import android.content.Context
import com.example.healthhub.Models.NewsApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.converter.gson.GsonConverterFactory

class RequestManager(private val context: Context) {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun getNewsHeadlines(listener: OnFetchDataListener<NewsApiResponse>, category: String, query: String) {
        val callNewsAPI = retrofit.create(CallNewsAPI::class.java)
        val call: Call<NewsApiResponse> = callNewsAPI.callHeadlines("us", category, query, context.getString(R.string.api_key))

        try {
            call.enqueue(object : Callback<NewsApiResponse> {
                override fun onResponse(call: Call<NewsApiResponse>, response: Response<NewsApiResponse>) {
                    if (response.isSuccessful) {
//                        val newsApiResponse: NewsApiResponse = response.body()
                        if (response.body() != null) {
                            response.body()?.articles?.let { listener.onFetchData(it, response.message()) }
                        } else {
                            listener.onError("Response body is null")
                        }
                    } else {
                        listener.onError("Failed to fetch data")
                    }
                }

                override fun onFailure(call: Call<NewsApiResponse>, t: Throwable) {
                    listener.onError(t.message ?: "Unknown error")
                }
            })
        } catch (e: Exception) {
            listener.onError("Exception: ${e.message}")
        }
    }


    interface CallNewsAPI {
        @GET("top-headlines") // Replace with your actual API endpoint
        fun callHeadlines(
            @Query("country") country: String,
            @Query("category") category: String,
            @Query("q") query: String,
            @Query("apiKey") apiKey: String
        ): Call<NewsApiResponse>
    }
}
