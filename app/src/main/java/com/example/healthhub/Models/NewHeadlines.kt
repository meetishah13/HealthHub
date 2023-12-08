package com.example.healthhub.Models
import com.example.healthhub.Models.Source
import java.io.Serializable

public class NewHeadlines: Serializable{
    var source: Source? = null
        get() = field
        set(value) {
            field = value
        }

    var author: String = ""
        get() = field
        set(value) {
            field = value
        }

    var title: String = ""
        get() = field
        set(value) {
            field = value
        }

    var description: String = ""
        get() = field
        set(value) {
            field = value
        }

    var url: String = ""
        get() = field
        set(value) {
            field = value
        }

    var urlToImage: String = ""
        get() = field
        set(value) {
            field = value
        }

    var publishedAt: String = ""
        get() = field
        set(value) {
            field = value
        }

    var content: String = ""
        get() = field
        set(value) {
            field = value
        }
}
