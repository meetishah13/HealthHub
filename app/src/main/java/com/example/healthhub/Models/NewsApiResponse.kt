package com.example.healthhub.Models

import java.io.Serializable

public class NewsApiResponse: Serializable {
    var status: String? = null
        get() = field
        set(value) {
            field = value
        }

    var totalResults: Int = 0
        get() = field
        set(value) {
            field = value
        }

    var articles: List<NewHeadlines>? = null
        get() = field
        set(value) {
            field = value
        }
}
