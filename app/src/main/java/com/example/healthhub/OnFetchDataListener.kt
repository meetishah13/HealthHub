package com.example.healthhub

import com.example.healthhub.Models.NewHeadlines

interface OnFetchDataListener<NewsApiResponse> {
    fun onFetchData(list: List<NewHeadlines>, message: String)
    fun onError(message: String)
}
