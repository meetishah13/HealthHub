package com.example.healthhub

import com.example.healthhub.Models.NewHeadlines

interface SelectListener {
    fun onNewsClicked(headline: NewHeadlines)
}
